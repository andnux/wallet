package top.andnux.wallet.eos.blockchain.chain;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import top.andnux.wallet.eos.blockchain.cypto.util.HexUtils;
import top.andnux.wallet.eos.blockchain.types.EosType;
import top.andnux.wallet.eos.blockchain.types.TypeAccountName;
import top.andnux.wallet.eos.blockchain.types.TypeActionName;
import top.andnux.wallet.eos.blockchain.types.TypePermissionLevel;

/**
 * Created by swapnibble on 2017-09-12.
 */

public class Action implements EosType.Packer, Serializable {
    @Expose
    private TypeAccountName account;

    @Expose
    private TypeActionName name;

    @Expose
    private List<TypePermissionLevel> authorization = null;

    @Expose
    private JsonElement data;

    public Action(String account, String name, List<TypePermissionLevel> authorization, String data) {
        this.account = new TypeAccountName(account);
        this.name = new TypeActionName(name);
        this.authorization = new ArrayList<>();
        if (null != authorization) {
            this.authorization.addAll(authorization);
        }

        if (null != data) {
            this.data = new JsonPrimitive(data);
        }
    }

    public Action(String account, String name, ArrayList<String> authorization, String data) {
        this.account = new TypeAccountName(account);
        this.name = new TypeActionName(name);
        this.authorization = new ArrayList<>();
        if (null != authorization) {
            for (String s : authorization) {
                String[] strings = s.split("@");
                this.authorization.add(new TypePermissionLevel(strings[0], strings[1]));
            }
        }

        if (null != data) {
            this.data = new JsonPrimitive(data);
        }
    }


    public Action(String account, String name, TypePermissionLevel authorization, String data) {
        this.account = new TypeAccountName(account);
        this.name = new TypeActionName(name);
        this.authorization = new ArrayList<>();
        if (null != authorization) {
            this.authorization.add(authorization);
        }

        if (null != data) {
            this.data = new JsonPrimitive(data);
        }
    }

    public String getAccount() {
        return account.toString();
    }

    public void setAccount(String account) {
        this.account = new TypeAccountName(account);
    }

    public String getName() {
        return name.toString();
    }

    public void setName(String name) {
        this.name = new TypeActionName(name);
    }

    public List<TypePermissionLevel> getAuthorization() {
        return authorization;
    }

    public void setAuthorization(List<TypePermissionLevel> authorization) {
        this.authorization = authorization;
    }

    public void setAuthorization(TypePermissionLevel[] authorization) {
        this.authorization.addAll(Arrays.asList(authorization));
    }

    public void setAuthorization(String[] accountWithPermLevel) {
        if (null == accountWithPermLevel) {
            return;
        }

        for (String permissionStr : accountWithPermLevel) {
            String[] split = permissionStr.split("@", 2);
            authorization.add(new TypePermissionLevel(split[0], split[1]));
        }
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(String data) {
        this.data = new JsonPrimitive(data);
    }

    @Override
    public void pack(EosType.Writer writer) {
        account.pack(writer);
        name.pack(writer);

        writer.putCollection(authorization);

        if (null != data) {
            byte[] dataAsBytes = HexUtils.toBytes(data.getAsString());
            writer.putVariableUInt(dataAsBytes.length);
            writer.putBytes(dataAsBytes);
        } else {
            writer.putVariableUInt(0);
        }
    }
}