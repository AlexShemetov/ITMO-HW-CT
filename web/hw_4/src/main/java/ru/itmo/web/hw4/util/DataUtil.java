package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Color;
import ru.itmo.web.hw4.model.User;
import ru.itmo.web.hw4.model.Post;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", 1, Color.RED),
            new User(6, "pashka", "Pavel Mavrin", 1, Color.BLUE),
            new User(9, "geranazavr555", "Georgiy Nazarov", 1, Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", 0, Color.GREEN)
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "Hello everyone!", "I'm create a codeforces! I hope you enjoy this site and don't test me too harshly", 1),
            new Post(8, "What this?", "I found something interesting in this app", 9),
            new Post(438, "Random keyboard tap", "kfdhghfdghfd;ghldsgih hdsghfdhsl g;hld gh;dhgldfshlg fdsh ;ghidg hdlfhsgh;fdshg lfdshg lkdshfg hrheg lhdslfhg lksfdhjg ;dslfjhgreg hdsflg hlksjdfg wrhelghdfg uhlwrhge ;lhdsghsfdg fdlgjh dgh;rwehgner gblwer g; krgndkf nkhdf;h nlfdnh lnfdk hfdsgnfds;hnfdshj;fdsh fd;hnfd;sh kfdshfdlfdshlkfdj ns;fdh kjfd hkwrnleh ;rw hwf dglw lhwk fjh;wj fgsfdjg;fds;g fd;ksjh fdsh ;fdsh j;fdj ;hklfj;lkdsh jfd;sjhj ;fdh jw;rh;w ", 6)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
