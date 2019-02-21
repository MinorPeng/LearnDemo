package com.example.serialportdemo

import android.util.Log
import android_serialport_api.SerialPort
import java.io.File
import java.io.IOException

/**
 * @author phs on 19-2-12
 */
abstract class WeightUtil {

    private var mPath: String = "/dev/ttyS0"
    private var mBaudrate = 9600
    private val mTimerDelay = 150
    private var mReadDelay = 50
    private var mSerialPort: SerialPort? = null
    var mReadWeightOrder = byteArrayOf(0x55, 0xAA.toByte())

    private var mReadThread: ReadThread? = null
    private var mGetThread: GetWeightThreadForST? = null
    private var mIsRun: Boolean = false

    fun open(): Boolean {
        try {
            mSerialPort = getSerialPortPrinter()
            if (mSerialPort?.getOutputStream() == null || mSerialPort?.getInputStream() == null) {
                return false
            }
            mIsRun = true
            if (mReadThread == null) {
                mReadThread = ReadThread()
            }
            mReadThread?.start()

            if (mGetThread == null) {
                mGetThread = GetWeightThreadForST()
            }
            mGetThread?.start()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun closeSerialPort() {
        if (mSerialPort != null) {
            mSerialPort?.close()
            mSerialPort = null
        }
        mSerialPort = null
        if (mGetThread != null) {
            mGetThread?.stopRun()
            mGetThread = null
        }
        if (mReadThread != null) {
            mReadThread?.interrupt()
            mGetThread = null
        }
    }

    fun getSerialPortPrinter(): SerialPort {
        if (mSerialPort == null) {
            mSerialPort = SerialPort(File(mPath), mBaudrate, 0)
        }
        return mSerialPort!!
    }

    inner class GetWeightThreadForST : Thread() {
        private var isRun: Boolean = false

        override fun start() {
            isRun = true
            super.start()
        }

        fun stopRun() {
            isRun = false
            interrupt()
        }

        override fun run() {
            super.run()
            while (isRun) {
                try {
                    getSerialPortPrinter().getOutputStream().write(mReadWeightOrder)
                    sleep(mTimerDelay.toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    inner class ReadThread : Thread() {
        override fun run() {
            super.run()
            while (mIsRun) {
                var size: Int
                try {
                    /**
                     * 这里的read要尤其注意，它会一直等待数据，等到天荒地老，海枯石烂。如果要判断是否接受完成，只有设置结束标识，
                     * 或作其他特殊的处理。
                     */
                    var buffer = byteArrayOf(64)
                    if (mSerialPort == null) {
                        return
                    }
                    size = mSerialPort?.getInputStream()?.read(buffer)!!
                    if (size > 0) {
                        var str = String(buffer, 0, size).trim()
                        if (str.contains("kg")) {
                            var index = str.indexOf("kg")
                            //TODO 注意返回数据的格式以及位数
                            Log.e("WeightUtil", str)
                            str = str.substring(0, index).replace(" ", " ")
                            try {
                                var b = str.toDouble()
                                onDataReceived(b)
                            } catch (e: Exception) {

                            }
                        }
                    }
                    Thread.sleep(mReadDelay.toLong())
                } catch (e: Exception) {
                    e.printStackTrace()
                    return
                }
            }
        }
    }

    /**
     * TODO 开发后期，采用接口注入的方式
     */
    protected abstract fun onDataReceived(d: Double)

    /**
     * 置0
     */
    fun resetBalance() {
        try {
            var order = byteArrayOf(0x55, 0x00)
            getSerialPortPrinter().getOutputStream().write(order)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun resetBalance(order: ByteArray) {
        try {
            getSerialPortPrinter().getOutputStream().write(order)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun suspend() {
        if (mGetThread != null) {
            mGetThread?.stopRun()
            mGetThread?.interrupt()
            mGetThread = null
        }
    }

    fun restart() {
        mGetThread = GetWeightThreadForST()
        mGetThread?.start()
    }

    fun setBaudrate(baudrate: Int) {
        this.mBaudrate = baudrate
    }

    fun setPath(path: String) {
        this.mPath = path
    }
}

