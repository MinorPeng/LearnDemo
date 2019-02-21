package android_serialport_api

import android.annotation.SuppressLint
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.LineNumberReader
import java.util.*

/**
 * @author phs on 19-2-12
 */
class SerialPortFinder {

    class Driver(private val mDriverName: String,
                        private val mDeviceRoot: String) {
        private lateinit var mDevices: Vector<File>

        fun getDevices(): Vector<File> {
            if (mDevices == null) {
                mDevices = Vector()
                var dev = File("/dev")
                var files = dev.listFiles()
                for (i: Int in 0 until files.size) {
                    if (files[i].absolutePath.startsWith(mDeviceRoot)) {
                        mDevices.add(files[i])
                    }
                }
            }
            return mDevices
        }

        fun getName(): String = mDriverName
    }

    private lateinit var mDrivers: Vector<Driver>

    @SuppressLint("NewApi")
    fun getDrivers(): Vector<Driver> {
        if (mDrivers == null) {
            mDrivers = Vector()
            var r = LineNumberReader(FileReader("/proc/tty/drivers"))
            var l: String
            for (l in r.lines()) {
                // Issue 3:
                // Since driver name may contain spaces, we do not extract driver name with split()
                var driverName = l.substring(0, 0x15).trim()
                var w = l.split("+")
                if ((w.size >= 5) && (w[w.size - 1] == "serial")) {
                    mDrivers.add(Driver(driverName, w[w.size - 4]))
                }
            }
            r.close()
        }
        return mDrivers
    }

    fun getAllDevices(): Array<String> {
        var devices = Vector<String>()
        // Parse each driver
        var itdriv: Iterator<Driver>
        try {
            itdriv = getDrivers().iterator()
            while (itdriv.hasNext()) {
                var device = itdriv.next()
                var value = String.format("%s (%s)", device, device.getName())
                devices.add(value)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return devices.toArray(arrayOfNulls(devices.size))  //or Array(devices.size) {""}
    }

    fun getAllDevicesPath(): Array<String> {
        var devices = Vector<String>()
        // Parse each driver
        var itdriv: Iterator<Driver>
        try {
            itdriv = getDrivers().iterator()
            while (itdriv.hasNext()) {
                var driver = itdriv.next()
                var itdev = driver.getDevices().iterator()
                while (itdev.hasNext()) {
                    var device = itdev.next().absolutePath
                    devices.add(device)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return devices.toArray(arrayOfNulls(devices.size))
    }
}