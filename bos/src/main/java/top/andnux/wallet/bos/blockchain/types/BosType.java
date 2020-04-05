package top.andnux.wallet.bos.blockchain.types;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by swapnibble on 2017-09-12.
 */

public interface BosType extends Serializable {
    class InsufficientBytesException extends Exception {

        private static final long serialVersionUID = 1L;
    }

    interface Packer extends Serializable {
        void pack(Writer writer);
    }

    interface Unpacker extends Serializable {
        void unpack(Reader reader) throws InsufficientBytesException;
    }

    interface Reader extends Serializable {
        byte get() throws InsufficientBytesException;

        int getShortLE() throws InsufficientBytesException;

        int getIntLE() throws InsufficientBytesException;

        long getLongLE() throws InsufficientBytesException;

        byte[] getBytes(int size) throws InsufficientBytesException;

        String getString() throws InsufficientBytesException;

        long getVariableUint() throws InsufficientBytesException;
    }

    interface Writer  extends Serializable{
        void put(byte b);

        void putShortLE(short value);

        void putIntLE(int value);

        void putLongLE(long value);

        void putBytes(byte[] value);

        void putString(String value);

        byte[] toBytes();

        int length();

        void putCollection(Collection<? extends Packer> collection);

        void putVariableUInt(long val);
    }
}
