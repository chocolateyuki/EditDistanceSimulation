public interface CommandVisitor<Integer> {
    void visitInsertCommand(Integer object);
    void visitKeepCommand(Integer object);
    void visitDeleteCommand(Integer object);
}
