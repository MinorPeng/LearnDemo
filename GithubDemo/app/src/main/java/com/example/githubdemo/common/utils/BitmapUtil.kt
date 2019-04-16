package com.example.githubdemo.common.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.FileNotFoundException


/**
 * @author 14512 on 2019/3/11
 */
object BitmapUtil {
    fun Bitmap2Bytes(bm: Bitmap): ByteArray {

        val baos = ByteArrayOutputStream()

        bm.compress(Bitmap.CompressFormat.PNG, 100, baos)

        return baos.toByteArray()
    }

    //byte[] → Bitmap

    fun Bytes2Bimap(b: ByteArray): Bitmap? {

        return if (b.isNotEmpty()) {

            BitmapFactory.decodeByteArray(b, 0, b.size)
        } else {

            null
        }
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {

        val bitmap = Bitmap.createBitmap(

            drawable.intrinsicWidth,

            drawable.intrinsicHeight,

            if (drawable.opacity != PixelFormat.OPAQUE)
                Bitmap.Config.ARGB_8888
            else
                Bitmap.Config.RGB_565
        )

        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)

        drawable.draw(canvas)

        return bitmap
    }

    fun bitmapToDrawable(resources: Resources, bitmap: Bitmap): Drawable {
        return BitmapDrawable(resources, bitmap)
    }

    /**
     * 从原bitmap头部开始 截取n行像素，生成新的bitmap
     *
     * @param bitmap 原始图像
     * @param rows 需要截取的行数(从顶部开始)
     */
    fun createRowPixsBitmap(bitmap: Bitmap?, rows: Int): Bitmap? {
        var rows = rows

        if (bitmap == null) {
            return null
        } else {

            val height = bitmap.height
            val width = bitmap.width

            rows = Math.min(Math.abs(rows), height)
            val colors = IntArray(width * rows)

            for (x in 0 until rows) {
                for (y in 0 until width) {
                    colors[x * width + y] = bitmap.getPixel(y, x)
                }
            }

            return Bitmap.createBitmap(colors, width, rows, bitmap.config)
        }
    }

    // 缓存bitmap 字节流
    fun getBytes(bitmap: Bitmap): ByteArray {
        //实例化字节数组输出流
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, baos)//压缩位图
        return baos.toByteArray()//创建分配字节数组
    }

    fun getBitmap(data: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(data, 0, data.size)//从字节数组解码位图
    }

    /**
     * 在内存中压缩图片
     *
     * @param resourceId 资源的id
     * @param requestWidth 需要压缩后的图片宽度
     * @param requestHeight 需要压缩后的图片高度
     * @return 压缩后的bitmap
     */
    fun compressBitmapInMemory(context: Context, resourceId: Int, requestWidth: Int, requestHeight: Int): Bitmap {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(context.resources, resourceId, options)
        options.inJustDecodeBounds = false

        val width = options.outWidth
        val height = options.outHeight

        options.inSampleSize = Math.max(width / requestWidth, height / requestHeight)

        return BitmapFactory.decodeResource(context.resources, resourceId, options)
    }

    //根据路径获取用户选择的图片
    fun sampleSizeImage(imgPath: String, ratio: Int): Bitmap? {
        val options = BitmapFactory.Options()
        options.inSampleSize = ratio//直接设置它的压缩率，表示1/2
        var b: Bitmap? = null
        try {
            b = BitmapFactory.decodeFile(imgPath, options)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return b
    }

    fun decodeUriAsBitmap(uri: Uri, context: Context): Bitmap? {
        var bitmap: Bitmap
        try {
            bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            return null
        }

        return bitmap
    }

    fun compressImage(uri: Uri, context: Context): Bitmap? {
        var pic: Bitmap? = null
        try {
            pic = decodeUriAsBitmap(uri, context)
        } catch (e: Exception) {
            LogUtil.e("TAG", e.toString())
        }

        val imgWidth = pic!!.width
        val imgHeight = pic.height
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图
        if (imgWidth >= 360f) {
            val scale = 360f / imgWidth
            val mx = Matrix()
            mx.setScale(scale, scale)
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true)
        }

        return pic
    }


    fun compressImage(uri: Uri, context: Context, insertedImgWidth: Float): Bitmap? {

        var pic: Bitmap? = null
        try {
            pic = decodeUriAsBitmap(uri, context)
        } catch (e: Exception) {
            LogUtil.e("TAG", e.toString())
        }

        val imgWidth = pic!!.width
        val imgHeight = pic.height
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图

        if (imgWidth >= insertedImgWidth) {
            val scale = insertedImgWidth / imgWidth
            val mx = Matrix()
            mx.setScale(scale, scale)
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true)
        }
        return pic
    }

    fun compressImage(byteArray: ByteArray?, insertedImgWidth: Float): Bitmap? {
        if (byteArray == null) {
            return null
        }
        var pic: Bitmap? = null
        try {
            pic = Bytes2Bimap(byteArray)
        } catch (e: Exception) {
            LogUtil.e("TAG", e.toString())
        }

        val imgWidth = pic!!.width
        val imgHeight = pic.height
        // 只对大尺寸图片进行下面的压缩，小尺寸图片使用原图

        if (imgWidth >= insertedImgWidth) {
            val scale = insertedImgWidth / imgWidth
            val mx = Matrix()
            mx.setScale(scale, scale)
            pic = Bitmap.createBitmap(pic, 0, 0, imgWidth, imgHeight, mx, true)
        }
        return pic
    }

}