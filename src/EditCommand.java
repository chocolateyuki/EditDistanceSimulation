public abstract class EditCommand<Integer> {
    private final Integer object;

    protected EditCommand(final Integer object) {
        this.object = object;
    }

    protected Integer getObject() {
        return object;
    }

    public abstract void accept(CommandVisitor<Integer> visitor);
}
