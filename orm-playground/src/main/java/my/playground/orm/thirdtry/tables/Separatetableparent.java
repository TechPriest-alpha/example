/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables;


import my.playground.orm.thirdtry.DefaultSchema;
import my.playground.orm.thirdtry.Keys;
import my.playground.orm.thirdtry.tables.records.SeparatetableparentRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Separatetableparent extends TableImpl<SeparatetableparentRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>SeparateTableParent</code>
     */
    public static final Separatetableparent SEPARATETABLEPARENT = new Separatetableparent();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SeparatetableparentRecord> getRecordType() {
        return SeparatetableparentRecord.class;
    }

    /**
     * The column <code>SeparateTableParent.id</code>.
     */
    public final TableField<SeparatetableparentRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>SeparateTableParent.parentData</code>.
     */
    public final TableField<SeparatetableparentRecord, String> PARENTDATA = createField(DSL.name("parentData"), SQLDataType.VARCHAR(255), this, "");

    private Separatetableparent(Name alias, Table<SeparatetableparentRecord> aliased) {
        this(alias, aliased, null);
    }

    private Separatetableparent(Name alias, Table<SeparatetableparentRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>SeparateTableParent</code> table reference
     */
    public Separatetableparent(String alias) {
        this(DSL.name(alias), SEPARATETABLEPARENT);
    }

    /**
     * Create an aliased <code>SeparateTableParent</code> table reference
     */
    public Separatetableparent(Name alias) {
        this(alias, SEPARATETABLEPARENT);
    }

    /**
     * Create a <code>SeparateTableParent</code> table reference
     */
    public Separatetableparent() {
        this(DSL.name("SeparateTableParent"), null);
    }

    public <O extends Record> Separatetableparent(Table<O> child, ForeignKey<O, SeparatetableparentRecord> key) {
        super(child, key, SEPARATETABLEPARENT);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<SeparatetableparentRecord> getPrimaryKey() {
        return Keys.SEPARATETABLEPARENT__;
    }

    @Override
    public Separatetableparent as(String alias) {
        return new Separatetableparent(DSL.name(alias), this);
    }

    @Override
    public Separatetableparent as(Name alias) {
        return new Separatetableparent(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Separatetableparent rename(String name) {
        return new Separatetableparent(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Separatetableparent rename(Name name) {
        return new Separatetableparent(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<Long, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}
