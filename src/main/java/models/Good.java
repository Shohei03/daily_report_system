package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * いいね取得データのDTOモデル
 *
 */

@Table(name = JpaConst.TABLE_GOOD)

@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_GOOD_COUNT_OF_REPORT,
            query = JpaConst.Q_GOOD_COUNT_OF_REPORT_DEF),
    @NamedQuery(
            name = JpaConst.Q_GOOD_CHANGE,
            query = JpaConst.Q_GOOD_CHANGE_DEF),
    @NamedQuery(
            name = JpaConst.Q_GOOD_GET,
            query = JpaConst.Q_GOOD_GET_DEF)
})


@Getter  // 全てのクラスフィールドについてgetterを自動生成する（Lombok）
@Setter  // 全てのクラスフィールドについてsetterを自動生成する（Lombok）
@NoArgsConstructor  // 引数なしコンストラクタを自動生成する（Lombok）
@AllArgsConstructor  // 全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する（Lombok）
@Entity
public class Good {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.GOOD_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * ログインした従業員情報
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.GOOD_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * 閲覧レポート
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.GOOD_COL_REP, nullable = false)
    private Report report;

}
