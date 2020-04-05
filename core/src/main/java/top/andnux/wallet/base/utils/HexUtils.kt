package top.andnux.wallet.base.utils

import java.nio.charset.StandardCharsets
import kotlin.experimental.and

/**
 * Created by LinkToken on 2018/4/26.
 * Utilities for going to and from ASCII-HEX representation.
 */

class HexUtils {
    companion object {
        /**
         * Encodes an array of bytes as hex symbols.
         *
         * @param bytes
         * the array of bytes to encode
         * @param separator
         * the separator to use between two bytes, can be null
         * @return the resulting hex string
         */
        @JvmOverloads
        @JvmStatic
        fun toHex(bytes: ByteArray, separator: String? = null): String {
            return toHex(bytes, 0, bytes.size, separator)
        }

        /**
         * Encodes a single byte to hex symbols.
         *
         * @param b the byte to encode
         * @return the resulting hex string
         */
        @JvmStatic
        fun toHex(b: Byte): String {
            val sb = StringBuilder()
            appendByteAsHex(sb, b)
            return sb.toString()
        }


        /**
         * Encodes an array of bytes as hex symbols.
         *
         * @param bytes
         * the array of bytes to encode
         * @param offset
         * the start offset in the array of bytes
         * @param length
         * the number of bytes to encode
         * @param separator
         * the separator to use between two bytes, can be null
         * @return the resulting hex string
         */
        @JvmOverloads
        @JvmStatic
        fun toHex(bytes: ByteArray, offset: Int, length: Int, separator: String? = null): String {
            val result = StringBuilder()
            for (i in 0 until length) {
                val unsignedByte = bytes[i + offset] and 0xff.toByte()
                if (unsignedByte < 16) {
                    result.append("0")
                }
                result.append(Integer.toHexString(unsignedByte.toInt()))
                if (separator != null && i + 1 < length) {
                    result.append(separator)
                }
            }
            return result.toString()
        }

        /**
         * Get the byte representation of an ASCII-HEX string.
         *
         * @param hexString
         * The string to convert to bytes
         * @return The byte representation of the ASCII-HEX string.
         */
        @JvmStatic
        fun toBytes(hexString: String?): ByteArray {
            if (hexString == null || hexString.length % 2 != 0) {
                throw RuntimeException("Input string must contain an even number of characters")
            }
            val hex = hexString.toCharArray()
            val length = hex.size / 2
            val raw = ByteArray(length)
            for (i in 0 until length) {
                val high = Character.digit(hex[i * 2], 16)
                val low = Character.digit(hex[i * 2 + 1], 16)
                if (high < 0 || low < 0) {
                    throw RuntimeException("Invalid hex digit " + hex[i * 2] + hex[i * 2 + 1])
                }
                var value = high shl 4 or low
                if (value > 127)
                    value -= 256
                raw[i] = value.toByte()
            }
            return raw
        }

        @JvmStatic
        fun toBytesReversed(hexString: String): ByteArray {
            val rawBytes = toBytes(hexString)

            for (i in 0 until rawBytes.size / 2) {
                val temp = rawBytes[rawBytes.size - i - 1]
                rawBytes[rawBytes.size - i - 1] = rawBytes[i]
                rawBytes[i] = temp
            }

            return rawBytes
        }

        @JvmStatic
        fun appendByteAsHex(sb: StringBuilder, b: Byte) {
            val unsignedByte = b and 0xFF.toByte()
            if (unsignedByte < 16) {
                sb.append("0")
            }
            sb.append(Integer.toHexString(unsignedByte.toInt()))
        }

        /**
         * 字符串转换为16进制字符串
         *
         * @param s
         * @return
         */
        @JvmStatic
        fun stringToHex(s: String): String {
            val str = StringBuilder()
            for (i in 0 until s.length) {
                val ch = s[i].toInt()
                val s4 = Integer.toHexString(ch)
                str.append(s4)
            }
            return str.toString()
        }


        /**
         * 16进制字符串转换为字符串
         *
         * @param s
         * @return
         */
        @JvmStatic
        fun hexToString(str: String?): String? {
            var s = str
            if (s == null || s == "") {
                return null
            }
            s = s.replace(" ", "")
            val baKeyword = ByteArray(s.length / 2)
            for (i in baKeyword.indices) {
                try {
                    baKeyword[i] = (0xff and Integer.parseInt(
                        s.substring(i * 2, i * 2 + 2), 16
                    )).toByte()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            try {
                s = String(baKeyword, StandardCharsets.UTF_8)
            } catch (e1: Exception) {
                e1.printStackTrace()
            }

            return s
        }
    }
}