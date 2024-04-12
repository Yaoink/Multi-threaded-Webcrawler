import java.util.List;
import java.util.stream.Collectors;

public class printProcess2 {
    public static void main(String[] args) {
        ProcessHandle.allProcesses().forEach(processHandle -> {
            System.out.println(processHandle.pid()+" "+processHandle.info());
        });
    }
}
