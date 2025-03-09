package com.github.ifuyo.apps.navigation.DeepSeek.dialogue;

import lombok.Getter;

public class DialogueController {
    private final DialogueView view;
    private final DialogueModel model;

    public DialogueController(DialogueView view, DialogueModel model) {
        this.view = view;
        this.model = model;
    }
}
