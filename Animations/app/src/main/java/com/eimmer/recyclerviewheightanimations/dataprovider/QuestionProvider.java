package com.eimmer.recyclerviewheightanimations.dataprovider;

public class QuestionProvider {
    public static Question[] getQuestions(){
        return new Question[]{
                new Question("Why are my contributions not showing up on my profile?", "Your profile contributions graph is a record of contributions you've made to GitHub repositories. Contributions are only counted if they meet certain criteria. In some cases, we may need to rebuild your graph in order for contributions to appear."),
                new Question("Why is Git always asking for my password?", "\n" +
                        "If Git prompts you for a username and password every time you try to interact with GitHub, you're probably using the HTTPS clone URL for your repository.\n" +
                        "\n" +
                        "Using an HTTPS remote URL has some advantages: it's easier to set up than SSH, and usually works through strict firewalls and proxies. However, it also prompts you to enter your GitHub credentials every time you pull or push a repository.\n" +
                        "\n" +
                        "You can configure Git to store your password for you. If you'd like to set that up, read all about setting up password caching."),
                new Question("HTTPS cloning errors", "There are a few common errors when using HTTPS with Git. These errors usually indicate you have an old version of Git, or you don't have access to the repository."),
                new Question("What is my disk quota?", "GitHub doesn't have any set disk quota.")
        };
    }
}
