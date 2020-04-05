package top.andnux.wallet.base

import androidx.annotation.Keep

@Keep
data class WalletAccount(
        var privateKey: String,
        var publicKey: String,
        var address: String? = null,
        var mnemonic: String? = null,
        var keystore: String? = null
)  