package top.andnux.wallet.base.utils

import io.github.novacrypto.bip39.MnemonicGenerator
import io.github.novacrypto.bip39.WordList
import io.github.novacrypto.bip39.Words
import io.github.novacrypto.bip39.wordlists.English
import org.bitcoinj.core.ECKey
import org.bitcoinj.crypto.ChildNumber
import org.bitcoinj.crypto.HDKeyDerivation
import org.bitcoinj.crypto.MnemonicCode
import java.security.SecureRandom

class ChainUtil {
    companion object {
        @JvmStatic
        @JvmOverloads
        fun genECKey(mnemonic: String, path: String, passphrase: String = ""): ECKey {
            val seedBytes = MnemonicCode.toSeed(mnemonic.split(" "), passphrase)
            var dkKey = HDKeyDerivation.createMasterPrivateKey(seedBytes)
            val pathArray = path.split("/".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            for (i in 1 until pathArray.size) {
                val childNumber: ChildNumber
                if (pathArray[i].endsWith("'")) {
                    val number = Integer.parseInt(pathArray[i].substring(0, pathArray[i].length - 1))
                    childNumber = ChildNumber(number, true)
                } else {
                    val number = Integer.parseInt(pathArray[i])
                    childNumber = ChildNumber(number, false)
                }
                dkKey = HDKeyDerivation.deriveChildKey(dkKey, childNumber)
            }
            return ECKey.fromPrivate(dkKey.privKeyBytes)
        }


        @JvmStatic
        @JvmOverloads
        fun genMnemonic(
                words: Words = Words.EIGHTEEN,
                wordList: WordList = English.INSTANCE
        ): String {
            val sb = StringBuilder()
            val entropy = ByteArray(words.byteLength())
            val secureRandom = SecureRandom()
            secureRandom.nextBytes(entropy)
            MnemonicGenerator(wordList).createMnemonic(entropy) { s: CharSequence? -> sb.append(s) }
            return sb.toString()
        }

        @JvmStatic
        @JvmOverloads
        fun genMnemonicList(
                words: Words = Words.EIGHTEEN,
                wordList: WordList = English.INSTANCE
        ): List<String> {
            val sb = StringBuilder()
            val entropy = ByteArray(words.byteLength())
            val secureRandom = SecureRandom()
            secureRandom.nextBytes(entropy)
            MnemonicGenerator(wordList).createMnemonic(entropy) { s: CharSequence? ->
                sb.append(s).append(" ")
            }
            return sb.toString().split(" ")
        }
    }
}