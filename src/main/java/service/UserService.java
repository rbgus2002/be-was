package service;

import db.Users;
import model.User;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static db.Users.findUserById;

public class UserService {
    private static ConcurrentMap<String, Lock> locks = new ConcurrentHashMap<>();
    private static Lock getLock(String userId) {
        return locks.computeIfAbsent(userId, k -> new ReentrantLock());
    }
    public static boolean addUser(User user) {
        Lock lock = getLock(user.getUserId());

        lock.lock();

        if(findUserById(user.getUserId()) != null) {
            lock.unlock();
            return false;
        }

        Users.addUser(user);

        lock.unlock();
        return true;
    }
}
