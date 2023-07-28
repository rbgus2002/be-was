package was.db;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Maps;

import was.model.Board;
import was.model.User;

public class Database {
    private static Map<String, User> users = Maps.newConcurrentMap();
    private static Map<Integer, Board> boards = Maps.newConcurrentMap();
    private static AtomicInteger boardIndex = new AtomicInteger(1);

    public static void addUser(User user) {
        users.put(user.getUserId(), user);
    }
    public static void addBoard(Board board) {
        boards.put(boardIndex.getAndIncrement(), board);
    }
    public static Board findBoardByIndex(int index) {
        return boards.get(index);
    }
    public static User findUserById(String userId) {
        return users.get(userId);
    }

    public static Collection<User> findUserAll() {
        return users.values();
    }
    public static void deleteUserAll() {users.clear();}

    public static List<Board> findBoardAllOrderByAsc() {
        return IntStream.range(1, boardIndex.get())
            .mapToObj(boards::get)
            .collect(Collectors.toList());
    }

    public static void deleteBoardAll() {
        boards.clear();
    }
}
