import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String>list2=Arrays.asList(
                "Action movies",
                "Detectives",
                "Easy Reading",
                "Thriller",
                "Cool Detective",
                "Ironic Detective",
                "About maniacs",
                "Spy detective",
                "Criminal detective",
                "Classic Detective",
                "Political Detective",
                "Novels",
                "Horror",
                "Adventures",
                "Serious reading",
                "Biographies",
                "Business Literature",
                "Economic Management",
                "Career",
                "Marketing, PR, advertising",
                "Finance",
                "Business directories",
                "Personal finance",
                "Management",
                "Foreign business literature",
                "Personal effectiveness",
                "Time management",
                "Small business",
                "Sales",
                "Startups and business creation",
                "Corporate culture",
                "Banking",
                "Logistics",
                "Real estate",
                "Internet business",
                "Customer engagement and loyalty",
                "Office work",
                "Negotiations",
                "State and municipal management, political management",
                "About business is popular",
                "Securities / investments",
                "Accounting / taxation / audit",
                "Russian Practice",
                "Success Stories",
                "Internet Marketing",
                "Leadership",
                "Project Management",
                "Quality Management",
                "Financial Management",
                "Personnel Management",
                "Business processes",
                "Business Management,",
                "Drama",
                "Ancient Drama",
                "Comedy",
                "Scenario",
                "Drama, play"
        );
        List<String> list = Arrays.asList(
                "??????????????",
                "??????????????????",
                "???????????? ????????????",
                "??????????????",
                "???????????? ????????????????",
                "?????????????????????? ????????????????",
                "?????? ????????????????",
                "?????????????????? ????????????????",
                "???????????????????????? ????????????????",
                "???????????????????????? ????????????????",
                "???????????????????????? ????????????????",
                "????????????",
                "??????????",
                "??????????????????????",
                "?????????????????? ????????????",
                "??????????????????",
                "?????????????? ????????????????????",
                "???????????????????? ????????????????????",
                "??????????????",
                "??????????????????, PR, ??????????????",
                "??????????????",
                "????????????-??????????????????????",
                "???????????? ??????????????",
                "????????????????????",
                "???????????????????? ?????????????? ????????????????????",
                "???????????? ??????????????????????????",
                "????????-????????????????????",
                "?????????? ????????????",
                "??????????????",
                "???????????????? ?? ???????????????? ??????????????",
                "?????????????????????????? ????????????????",
                "???????????????????? ????????",
                "??????????????????",
                "????????????????????????",
                "????????????????-????????????",
                "?????????????????????? ???????????????? ?? ????????????????????",
                "????????????????????????????????",
                "????????????????????",
                "?????????????????????????????? ?? ?????????????????????????? ????????????????????, ???????????????????????? ????????????????????",
                "?? ?????????????? ??????????????????",
                "???????????? ???????????? / ????????????????????",
                "?????????????? / ?????????????????????????????? / ??????????",
                "???????????????????? ????????????????",
                "?????????????? ????????????",
                "????????????????-????????????????",
                "??????????????????",
                "?????????????????? ????????????????????",
                "???????????????????? ??????????????????",
                "???????????????????? ????????????????????",
                "???????????????????? ????????????????????",
                "????????????-????????????????",
                "???????????????????? ????????????????,",
                "??????????????????????",
                "???????????????? ??????????",
                "??????????????",
                "????????????????",
                "??????????, ??????????"
        );

       int i=0;
       int iterator=list.size();
       while (i<iterator){
           String str=list2.get(i).replaceAll(" ","");
           str=str.replaceAll("/","");
           str=str.replaceAll(",","");
           System.out.println("topbar."+str+"="+list2.get(i));
           i++;
       }
       i=0;
        System.out.println("=============================================");
        while (i<iterator){
            String str=list2.get(i).replaceAll(" ","");
            str=str.replaceAll("/","");
            str=str.replaceAll(",","");
            System.out.println("topbar."+str+"="+list.get(i));
            i++;
        }
        i=0;
        System.out.println("=============================================");

        while (i<iterator){
            String str=list2.get(i).replaceAll(" ","");
            str=str.replaceAll("/","");
            str=str.replaceAll(",","");
            System.out.println("th:text=\"#{topbar."+str+"}\"");
            i++;
        }
        System.out.println((char) 27 + "[31mWarning! " + (char)27 + "[0m");
    }

}
