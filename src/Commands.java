public class Commands {
    public static class InsertCommand<Integer> extends EditCommand<Integer> {
        public InsertCommand(final Integer object) {
            super(object);
        }

        @Override
        public void accept(final CommandVisitor<Integer> visitor) {
            visitor.visitInsertCommand(getObject());
        }
    }

    public static class DeleteCommand<Integer> extends EditCommand<Integer> {
        public DeleteCommand(final Integer object) {
            super(object);
        }

        @Override
        public void accept(final CommandVisitor<Integer> visitor) {
            visitor.visitDeleteCommand(getObject());
        }
    }

    public static class KeepCommand<Integer> extends EditCommand<Integer> {

        public KeepCommand(final Integer object) {
            super(object);
        }

        @Override
        public void accept(final CommandVisitor<Integer> visitor) {
            visitor.visitKeepCommand(getObject());
        }
    }
}
