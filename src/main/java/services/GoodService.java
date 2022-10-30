package services;

import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Good;

/**
 *
 * goodテーブルの操作に関わる処理を行うクラス
 *
 */

public class GoodService extends ServiceBase {
    /**
     * 日報のいいねボタンが押されると、goodテーブルにレコードを登録する
     * @param good いいねデータ
     *
     */
    public void create(Good good) {
        em.getTransaction().begin();
        em.persist(good);
        em.getTransaction().commit();
    }

    /**
     * 一致するいいねデータを削除する
     * @param id
     */
    public void destroy(ReportView rv, EmployeeView ev) {

        List<Good> good_list = new ArrayList<>();

        good_list = em.createNamedQuery(JpaConst.Q_GOOD_GET, Good.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev)).getResultList();

        for (Good good : good_list) {
            em.getTransaction().begin();
            em.remove(good);
            em.getTransaction().commit();

        }
    }

    /**
     * 指定した日報データのいいね件数を取得し、返却する
     * @param report
     * @return 指定した日報のいいね件数
     */
    public long count_report_good_num(ReportView rv) {

        long count = (long) em.createNamedQuery(JpaConst.Q_GOOD_COUNT_OF_REPORT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv)).getSingleResult();

        return count;
    }

    /**
     * 「いいね」ボタン⇔「いいね解除」ボタン
     *  numが0の場合、いいねデータベースに指定したレコードがない、num != 0の場合、データがある。
     *  trueなら、いいねボタンを表示、falseなら、いいね解除ボタンを表示するようになる。
     */
    // 日報idとログインしている従業員のidを取得し引数に指定する必要あり

    public long change(ReportView rv, EmployeeView ev) {
        long num = (long) em.createNamedQuery(JpaConst.Q_GOOD_CHANGE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(rv))
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(ev))
                .getSingleResult();
        System.out.println("num:" + num);
        return num;

    }

}
