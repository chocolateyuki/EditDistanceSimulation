import java.util.ArrayList;

public class Myers {
    int comparisonCount = 0;
    int editDistance = 0;

    public void runMyersAlgorithm(ArrayList<Integer> list1, ArrayList<Integer> list2) {
        IntegerComparator sc = new IntegerComparator(list1,  list2);
        EditScript<Integer> es = sc.getScript();
        CommandVisitor<Integer> tmp = new CommandVisitor<Integer>() {
            boolean nlAdd = true;
            boolean nlRemove = true;

            @Override
            public void visitInsertCommand(Integer object) {
                if (nlAdd) {
                    nlAdd = false;
                }
            }

            @Override
            public void visitKeepCommand(Integer object) {
                if (!nlAdd) {
                    nlAdd = true;
                }
                if (!nlRemove) {
                    nlRemove = true;
                }
            }

            @Override
            public void visitDeleteCommand(Integer object) {
                if (nlRemove) {
                    nlRemove = false;
                }
            }
        };

        es.visit(tmp);

        comparisonCount = sc.count;
        editDistance = es.getModifications();
    }
}

