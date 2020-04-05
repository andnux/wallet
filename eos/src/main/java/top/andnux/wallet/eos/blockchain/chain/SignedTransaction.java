package top.andnux.wallet.eos.blockchain.chain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import top.andnux.wallet.eos.blockchain.cypto.digest.Sha256;
import top.andnux.wallet.eos.blockchain.cypto.ec.EcDsa;
import top.andnux.wallet.eos.blockchain.cypto.ec.EcSignature;
import top.andnux.wallet.eos.blockchain.cypto.ec.EosPrivateKey;
import top.andnux.wallet.eos.blockchain.types.EosByteWriter;
import top.andnux.wallet.eos.blockchain.types.TypeChainId;

/**
 * Created by swapnibble on 2017-09-12.
 */

public class SignedTransaction extends Transaction implements Serializable {

    @Expose
    private List<String> signatures = null;

    @Expose
    private List<String> context_free_data = new ArrayList<>();


    public SignedTransaction() {
        super();
    }

    public SignedTransaction(SignedTransaction anotherTxn) {
        super(anotherTxn);
        this.signatures = deepCopyOnlyContainer(anotherTxn.signatures);
        this.context_free_data = context_free_data;
    }

    public List<String> getSignatures() {
        return signatures;
    }

    public void putSignatures(List<String> signatures) {
        this.signatures = signatures;
    }

    public int getCtxFreeDataCount() {
        return (context_free_data == null) ? 0 : context_free_data.size();
    }

    public List<String> getCtxFreeData() {
        return context_free_data;
    }


    private Sha256 getDigestForSignature(TypeChainId chainId) {
        EosByteWriter writer = new EosByteWriter(255);

        // data layout to sign :
        // [ {chainId}, {Transaction( parent class )}, {hash of context_free_data only when exists ]

        writer.putBytes(chainId.getBytes());
        pack(writer);
        if (context_free_data.size() > 0) {
        } else {
            writer.putBytes(Sha256.ZERO_HASH.getBytes());
        }

        return Sha256.from(writer.toBytes());
    }

    public void sign(EosPrivateKey privateKey, TypeChainId chainId) {
        if (null == this.signatures) {
            this.signatures = new ArrayList<>();
        }

        EcSignature signature = EcDsa.sign(getDigestForSignature(chainId), privateKey);
        this.signatures.add(signature.toString());
    }
}

