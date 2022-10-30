package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import models.Employee;
import models.Good;
import models.Report;
import services.GoodService;

/**
 *
 * いいね機能に関する処理を行うActionクラス
 *
 */

public class GoodAction extends ActionBase {

    private GoodService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {
        service = new GoodService();

        // メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */

    public void create() throws ServletException, IOException {
        // セッションからログイン中の従業員情報を取得

        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Employee employee = EmployeeConverter.toModel(ev);

        // 日報データを取得

        ReportView rv = (ReportView) getSessionScope(AttributeConst.REPORT);
        Report report = ReportConverter.toModel(rv);

        Good good = new Good(null, employee, report);

        service.create(good);

        //詳細画面にリダイレクト
        redirect(ForwardConst.ACT_GOOD, ForwardConst.CMD_SHOW);

    }

    /**
     * いいねレコードを削除する
     * @throws ServletException
     * @throws IOException
     */

    public void destroy() throws ServletException, IOException {
        // セッションからログイン中の従業員情報を取得
        System.out.println("destroy command実行");

        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // 日報データを取得

        ReportView rv = (ReportView) getSessionScope(AttributeConst.REPORT);

        // レコードを削除
        service.destroy(rv, ev);

        //詳細画面にリダイレクト
        redirect(ForwardConst.ACT_GOOD, ForwardConst.CMD_SHOW);

    }

    /**
     * 指定した日報のいいね件数を取得する
     * @throws ServletException
     * @throws IOException
     */

    public void show() throws ServletException, IOException {
        // 日報オブジェクトを取得
        ReportView rv = (ReportView) getSessionScope(AttributeConst.REPORT);

        // セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        // 指定した日報のいいね件数を取得する
        long good_count = service.count_report_good_num(rv); // 指定した日報のいいね件数

        // ログインしている従業員が過去に表示されている日報に「いいね」している場合、Trueを返し、「いいね」していない場合はFalseを返す。
        long good_judge = service.change(rv, ev);
        System.out.println("good_judge:" + good_judge);

        // 上記変数をinclude元に渡す
        putRequestScope(AttributeConst.GOOD_COUNT, good_count);
        putRequestScope(AttributeConst.GOOD_JUDGE, good_judge);
        putRequestScope(AttributeConst.REPORT, rv);

        // 詳細画面を表示
        forward(ForwardConst.FW_REP_SHOW);

    }

}
