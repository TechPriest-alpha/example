/*
 * This file is generated by jOOQ.
 */
package my.playground.orm.thirdtry;


import my.playground.orm.thirdtry.tables.*;
import org.jooq.Catalog;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;

import java.util.Arrays;
import java.util.List;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class DefaultSchema extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>DEFAULT_SCHEMA</code>
     */
    public static final DefaultSchema DEFAULT_SCHEMA = new DefaultSchema();

    /**
     * The table <code>author</code>.
     */
    public final Author AUTHOR = Author.AUTHOR;

    /**
     * The table <code>Ent1</code>.
     */
    public final Ent1 ENT1 = Ent1.ENT1;

    /**
     * The table <code>Ent1_SEQ</code>.
     */
    public final Ent1Seq ENT1_SEQ = Ent1Seq.ENT1_SEQ;

    /**
     * The table <code>Ent2</code>.
     */
    public final Ent2 ENT2 = Ent2.ENT2;

    /**
     * The table <code>Ent2_SEQ</code>.
     */
    public final Ent2Seq ENT2_SEQ = Ent2Seq.ENT2_SEQ;

    /**
     * The table <code>Ent3</code>.
     */
    public final Ent3 ENT3 = Ent3.ENT3;

    /**
     * The table <code>Ent3_SEQ</code>.
     */
    public final Ent3Seq ENT3_SEQ = Ent3Seq.ENT3_SEQ;

    /**
     * The table <code>JoinTableInheritor</code>.
     */
    public final Jointableinheritor JOINTABLEINHERITOR = Jointableinheritor.JOINTABLEINHERITOR;

    /**
     * The table <code>JoinTableParent</code>.
     */
    public final Jointableparent JOINTABLEPARENT = Jointableparent.JOINTABLEPARENT;

    /**
     * The table <code>JoinTableParent_SEQ</code>.
     */
    public final JointableparentSeq JOINTABLEPARENT_SEQ = JointableparentSeq.JOINTABLEPARENT_SEQ;

    /**
     * The table <code>mapped_email</code>.
     */
    public final MappedEmail MAPPED_EMAIL = MappedEmail.MAPPED_EMAIL;

    /**
     * The table <code>mapped_email_eager</code>.
     */
    public final MappedEmailEager MAPPED_EMAIL_EAGER = MappedEmailEager.MAPPED_EMAIL_EAGER;

    /**
     * The table <code>mapped_email_subject</code>.
     */
    public final MappedEmailSubject MAPPED_EMAIL_SUBJECT = MappedEmailSubject.MAPPED_EMAIL_SUBJECT;

    /**
     * The table <code>mapped_message</code>.
     */
    public final MappedMessage MAPPED_MESSAGE = MappedMessage.MAPPED_MESSAGE;

    /**
     * The table <code>mapped_message_content</code>.
     */
    public final MappedMessageContent MAPPED_MESSAGE_CONTENT = MappedMessageContent.MAPPED_MESSAGE_CONTENT;

    /**
     * The table <code>mapped_message_eager</code>.
     */
    public final MappedMessageEager MAPPED_MESSAGE_EAGER = MappedMessageEager.MAPPED_MESSAGE_EAGER;

    /**
     * The table <code>non_mapped_email</code>.
     */
    public final NonMappedEmail NON_MAPPED_EMAIL = NonMappedEmail.NON_MAPPED_EMAIL;

    /**
     * The table <code>non_mapped_message</code>.
     */
    public final NonMappedMessage NON_MAPPED_MESSAGE = NonMappedMessage.NON_MAPPED_MESSAGE;

    /**
     * The table <code>Person</code>.
     */
    public final Person PERSON = Person.PERSON;

    /**
     * The table <code>Person_SEQ</code>.
     */
    public final PersonSeq PERSON_SEQ = PersonSeq.PERSON_SEQ;

    /**
     * The table <code>Ranking</code>.
     */
    public final Ranking RANKING = Ranking.RANKING;

    /**
     * The table <code>Ranking_SEQ</code>.
     */
    public final RankingSeq RANKING_SEQ = RankingSeq.RANKING_SEQ;

    /**
     * The table <code>SeparateTableInheritor</code>.
     */
    public final Separatetableinheritor SEPARATETABLEINHERITOR = Separatetableinheritor.SEPARATETABLEINHERITOR;

    /**
     * The table <code>SeparateTableParent</code>.
     */
    public final Separatetableparent SEPARATETABLEPARENT = Separatetableparent.SEPARATETABLEPARENT;

    /**
     * The table <code>SeparateTableParent_SEQ</code>.
     */
    public final SeparatetableparentSeq SEPARATETABLEPARENT_SEQ = SeparatetableparentSeq.SEPARATETABLEPARENT_SEQ;

    /**
     * The table <code>SingleTableParent</code>.
     */
    public final Singletableparent SINGLETABLEPARENT = Singletableparent.SINGLETABLEPARENT;

    /**
     * The table <code>SingleTableParent_SEQ</code>.
     */
    public final SingletableparentSeq SINGLETABLEPARENT_SEQ = SingletableparentSeq.SINGLETABLEPARENT_SEQ;

    /**
     * The table <code>Skill</code>.
     */
    public final Skill SKILL = Skill.SKILL;

    /**
     * The table <code>Skill_SEQ</code>.
     */
    public final SkillSeq SKILL_SEQ = SkillSeq.SKILL_SEQ;

    /**
     * No further instances allowed
     */
    private DefaultSchema() {
        super("", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.asList(
            Author.AUTHOR,
            Ent1.ENT1,
            Ent1Seq.ENT1_SEQ,
            Ent2.ENT2,
            Ent2Seq.ENT2_SEQ,
            Ent3.ENT3,
            Ent3Seq.ENT3_SEQ,
            Jointableinheritor.JOINTABLEINHERITOR,
            Jointableparent.JOINTABLEPARENT,
            JointableparentSeq.JOINTABLEPARENT_SEQ,
            MappedEmail.MAPPED_EMAIL,
            MappedEmailEager.MAPPED_EMAIL_EAGER,
            MappedEmailSubject.MAPPED_EMAIL_SUBJECT,
            MappedMessage.MAPPED_MESSAGE,
            MappedMessageContent.MAPPED_MESSAGE_CONTENT,
            MappedMessageEager.MAPPED_MESSAGE_EAGER,
            NonMappedEmail.NON_MAPPED_EMAIL,
            NonMappedMessage.NON_MAPPED_MESSAGE,
            Person.PERSON,
            PersonSeq.PERSON_SEQ,
            Ranking.RANKING,
            RankingSeq.RANKING_SEQ,
            Separatetableinheritor.SEPARATETABLEINHERITOR,
            Separatetableparent.SEPARATETABLEPARENT,
            SeparatetableparentSeq.SEPARATETABLEPARENT_SEQ,
            Singletableparent.SINGLETABLEPARENT,
            SingletableparentSeq.SINGLETABLEPARENT_SEQ,
            Skill.SKILL,
            SkillSeq.SKILL_SEQ
        );
    }
}
