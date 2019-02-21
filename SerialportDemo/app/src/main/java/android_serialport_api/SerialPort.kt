package android_serialport_api

import java.io.*

/**
 * from google https://github.com/cepr/android-serialport-api/blob/master/android-serialport-api
 *
 * @author phs on 19-2-12
 */
class SerialPort(device: File, baudrate: Int, flags: Int) {

    private var mFd: FileDescriptor
    private var mFileInputStream: FileInputStream
    private var mFileOutputStream: FileOutputStream

    init {
        System.loadLibrary("SerialPort")

        /* Check access permission */
        if (!device.canRead() || !device.canWrite()) {
            try {
                /* Missing read/write permission, trying to chmod the file */
                var su: Process = Runtime.getRuntime().exec("/system/bin/su")
                var cmd = "chmod 666 ${device.absolutePath}\nexit\n"
                su.outputStream.write(cmd.toByteArray())
                if ((su.waitFor() != 0) || !device.canRead() || !device.canWrite()) {
                    throw SecurityException()
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw SecurityException()
            }
        }

        mFd = open(device.absolutePath, baudrate, flags)
        if (mFd == null) {
            throw IOException()
        }
        mFileInputStream = FileInputStream(mFd)
        mFileOutputStream = FileOutputStream(mFd)
    }

    // Getters and setters
    fun getInputStream(): InputStream {
        return mFileInputStream
    }

    fun getOutputStream(): OutputStream {
        return mFileOutputStream
    }

    /**
     * JNI
     */
    private external fun open(path: String, baudrate: Int, flags: Int): FileDescriptor

    external fun close()
}