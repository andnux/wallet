package top.andnux.wallet.base.services

import top.andnux.wallet.base.WalletAccount
import java.lang.Exception

interface WalletService {
    fun chainName(): String;

    @Throws(Exception::class)
    fun createAccount(): WalletAccount

    @Throws(Exception::class)
    fun createAccountByMnemonic(mnemonic: String, path: String): WalletAccount

    @Throws(Exception::class)
    fun createAccountByPrivateKey(privateKey: String): WalletAccount

    @Throws(Exception::class)
    fun createAccountByKeyStore(keyStore: String, password: String): WalletAccount
}