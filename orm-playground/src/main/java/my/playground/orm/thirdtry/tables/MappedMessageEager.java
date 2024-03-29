/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables;


import my.playground.orm.thirdtry.DefaultSchema;
import my.playground.orm.thirdtry.Keys;
import my.playground.orm.thirdtry.tables.records.MappedMessageEagerRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class MappedMessageEager extends TableImpl<MappedMessageEagerRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>mapped_message_eager</code>
     */
    public static final MappedMessageEager MAPPED_MESSAGE_EAGER = new MappedMessageEager();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MappedMessageEagerRecord> getRecordType() {
        return MappedMessageEagerRecord.class;
    }

    /**
     * The column <code>mapped_message_eager.id</code>.
     */
    public final TableField<MappedMessageEagerRecord, byte[]> ID = createField(DSL.name("id"), SQLDataType.BLOB.nullable(false), this, "");

    /**
     * The column <code>mapped_message_eager.email_id</code>.
     */
    public final TableField<MappedMessageEagerRecord, Long> EMAIL_ID = createField(DSL.name("email_id"), SQLDataType.BIGINT, this, "");

    private MappedMessageEager(Name alias, Table<MappedMessageEagerRecord> aliased) {
        this(alias, aliased, null);
    }

    private MappedMessageEager(Name alias, Table<MappedMessageEagerRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>mapped_message_eager</code> table reference
     */
    public MappedMessageEager(String alias) {
        this(DSL.name(alias), MAPPED_MESSAGE_EAGER);
    }

    /**
     * Create an aliased <code>mapped_message_eager</code> table reference
     */
    public MappedMessageEager(Name alias) {
        this(alias, MAPPED_MESSAGE_EAGER);
    }

    /**
     * Create a <code>mapped_message_eager</code> table reference
     */
    public MappedMessageEager() {
        this(DSL.name("mapped_message_eager"), null);
    }

    public <O extends Record> MappedMessageEager(Table<O> child, ForeignKey<O, MappedMessageEagerRecord> key) {
        super(child, key, MAPPED_MESSAGE_EAGER);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<MappedMessageEagerRecord> getPrimaryKey() {
        return Keys.MAPPED_MESSAGE_EAGER__;
    }

    @Override
    public MappedMessageEager as(String alias) {
        return new MappedMessageEager(DSL.name(alias), this);
    }

    @Override
    public MappedMessageEager as(Name alias) {
        return new MappedMessageEager(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MappedMessageEager rename(String name) {
        return new MappedMessageEager(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MappedMessageEager rename(Name name) {
        return new MappedMessageEager(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<byte[], Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
