/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry.tables;


import my.playground.orm.thirdtry.DefaultSchema;
import my.playground.orm.thirdtry.Keys;
import my.playground.orm.thirdtry.tables.records.SeparatetableinheritorRecord;
import org.jooq.Record;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Separatetableinheritor extends TableImpl<SeparatetableinheritorRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>SeparateTableInheritor</code>
     */
    public static final Separatetableinheritor SEPARATETABLEINHERITOR = new Separatetableinheritor();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<SeparatetableinheritorRecord> getRecordType() {
        return SeparatetableinheritorRecord.class;
    }

    /**
     * The column <code>SeparateTableInheritor.id</code>.
     */
    public final TableField<SeparatetableinheritorRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT.nullable(false), this, "");

    /**
     * The column <code>SeparateTableInheritor.parentData</code>.
     */
    public final TableField<SeparatetableinheritorRecord, String> PARENTDATA = createField(DSL.name("parentData"), SQLDataType.VARCHAR(255), this, "");

    /**
     * The column <code>SeparateTableInheritor.inheritorValue</code>.
     */
    public final TableField<SeparatetableinheritorRecord, String> INHERITORVALUE = createField(DSL.name("inheritorValue"), SQLDataType.VARCHAR(255), this, "");

    private Separatetableinheritor(Name alias, Table<SeparatetableinheritorRecord> aliased) {
        this(alias, aliased, null);
    }

    private Separatetableinheritor(Name alias, Table<SeparatetableinheritorRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>SeparateTableInheritor</code> table reference
     */
    public Separatetableinheritor(String alias) {
        this(DSL.name(alias), SEPARATETABLEINHERITOR);
    }

    /**
     * Create an aliased <code>SeparateTableInheritor</code> table reference
     */
    public Separatetableinheritor(Name alias) {
        this(alias, SEPARATETABLEINHERITOR);
    }

    /**
     * Create a <code>SeparateTableInheritor</code> table reference
     */
    public Separatetableinheritor() {
        this(DSL.name("SeparateTableInheritor"), null);
    }

    public <O extends Record> Separatetableinheritor(Table<O> child, ForeignKey<O, SeparatetableinheritorRecord> key) {
        super(child, key, SEPARATETABLEINHERITOR);
    }

    @Override
    public Schema getSchema() {
        return aliased() ? null : DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public UniqueKey<SeparatetableinheritorRecord> getPrimaryKey() {
        return Keys.SEPARATETABLEINHERITOR__;
    }

    @Override
    public Separatetableinheritor as(String alias) {
        return new Separatetableinheritor(DSL.name(alias), this);
    }

    @Override
    public Separatetableinheritor as(Name alias) {
        return new Separatetableinheritor(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Separatetableinheritor rename(String name) {
        return new Separatetableinheritor(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Separatetableinheritor rename(Name name) {
        return new Separatetableinheritor(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<Long, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
