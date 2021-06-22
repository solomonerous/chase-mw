package com.obulex.domain.interfaces;

public interface MutablePersistent<KEY_TYPE> extends OriemsEntity<KEY_TYPE> {
    /**
     *
     * @param id
     */
    public void setId( KEY_TYPE id );
}
