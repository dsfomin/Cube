package dsfomin.cube.domain;

public final class Views {
    public interface Id {}

    public interface IdName extends Id {}

    public interface FullMessage extends IdName {}

    public interface FullComment extends IdName {}

    public interface FullProfile extends IdName {}
}
