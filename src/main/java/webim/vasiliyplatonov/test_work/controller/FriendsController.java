package webim.vasiliyplatonov.test_work.controller;

import org.springframework.web.bind.annotation.*;
import webim.vasiliyplatonov.test_work.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/friends")
public class FriendsController {
    private int counter = 4;

    private List<Map<String, String>> friends = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{
            put("id", "1");
            put("text", "First friend");
        }});
        add(new HashMap<String, String>() {{
            put("id", "2");
            put("text", "Second friend");
        }});
        add(new HashMap<String, String>() {{
            put("id", "3");
            put("text", "Third friend");
        }});
    }};

    @GetMapping
    public List<Map<String, String>> friends() {
        return friends;
    }

    @GetMapping("/{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getFriend(id);
    }

    private Map<String, String> getFriend(String id) {
        return friends
                .stream()
                .filter(f -> f.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> friend) {
        friend.put("id", String.valueOf(counter++));
        friends.add(friend);

        return friend;
    }

    @PutMapping("/{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> friend) {
        Map<String, String> friendFromDb = getFriend(id);

        friendFromDb.putAll(friend);
        friendFromDb.put("id", id);

        return friendFromDb;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        Map<String, String> friend = getFriend(id);
        friends.remove(friend);
    }
}
