package top.andnux.wallet.bos.blockchain.chain;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import top.andnux.wallet.bos.blockchain.types.BosType;

/**
 * Created by swapnibble on 2017-09-12.
 */

public class Transaction extends TransactionHeader implements Serializable {
    @Expose
    private List<Action> context_free_actions = new ArrayList<>();

    @Expose
    private List<Action> actions = null;

    // Extentions are prefixed with type and are a buffer that can be interpreted by code that is aware and ignored by unaware code.
    @Expose
    private List<String> transaction_extensions = new ArrayList<>();

    public Transaction() {
        super();
    }


    public Transaction(Transaction other) {
        super(other);
        this.context_free_actions = deepCopyOnlyContainer(other.context_free_actions);
        this.actions = deepCopyOnlyContainer(other.actions);
        this.transaction_extensions = other.transaction_extensions;
    }

    public void addAction(Action msg) {
        if (null == actions) {
            actions = new ArrayList<>(1);
        }

        actions.add(msg);
    }


    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public int getContextFreeActionCount() {
        return (actions == null ? 0 : actions.size());
    }


    <T> List<T> deepCopyOnlyContainer(List<T> srcList) {
        if (null == srcList) {
            return null;
        }

        List<T> newList = new ArrayList<>(srcList.size());
        newList.addAll(srcList);

        return newList;
    }

    @Override
    public void pack(BosType.Writer writer) {
        super.pack(writer);

        writer.putCollection(context_free_actions);
        writer.putCollection(actions);
        //writer.putCollection(transaction_extensions);
        writer.putVariableUInt(transaction_extensions.size());
        if (transaction_extensions.size() > 0) {
            // TODO 구체적 코드가 나오면 확인후 구현할 것.
        }
    }
}