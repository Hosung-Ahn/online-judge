package com.oj.service.judge;

public enum Language {
    PYTHON {
        @Override
        public String getExtension() {
            return ".py";
        }

        @Override
        public String getRunner() {
            return "python_runner.sh";
        }
    },JAVA {
        @Override
        public String getExtension() {
            return ".java";
        }

        @Override
        public String getRunner() {
            return "java_runner.sh";
        }
    },CPP {
        @Override
        public String getExtension() {
            return ".cpp";
        }

        @Override
        public String getRunner() {
            return "cpp_runner.sh";
        }
    };

    public abstract String getExtension();
    public abstract String getRunner();
}
