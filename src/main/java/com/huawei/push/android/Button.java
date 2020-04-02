package com.huawei.push.android;

import com.alibaba.fastjson.annotation.JSONField;
import com.huawei.push.util.ValidatorUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author linjw
 * @date 2020/4/2 11:10
 */
public class Button {
    @JSONField(name = "name")
    private String name;

    @JSONField(name = "action_type")
    private Integer actionType;

    @JSONField(name = "intent_type")
    private Integer intentType;

    @JSONField(name = "intent")
    private String intent;

    @JSONField(name = "data")
    private String data;

    public Button(Builder builder) {
        this.name = builder.name;
        this.actionType = builder.actionType;
        this.intentType = builder.intentType;
        this.intent = builder.intent;
        this.data = builder.data;
    }

    public String getName() {
        return name;
    }

    public Integer getActionType() {
        return actionType;
    }

    public Integer getIntentType() {
        return intentType;
    }

    public String getIntent() {
        return intent;
    }

    public String getData() {
        return data;
    }

    public void check() {
        ValidatorUtils.checkArgument(StringUtils.isNotBlank(name), "name must be selected when buttons is set");

        ValidatorUtils.checkArgument(this.actionType != null, "action_type must be selected when buttons is set");

        if (actionType != null) {
            boolean isTrue = this.actionType == 0 ||
                    this.actionType == 1 ||
                    this.actionType == 2 ||
                    this.actionType == 3 ||
                    this.actionType == 4;
            ValidatorUtils.checkArgument(isTrue, "action_type should be one of 0: open application homepage," +
                    " 1: open application custom page," +
                    " 2: open the specified web page," +
                    " 3: clear notification," +
                    " 4: share function");

            if (this.actionType == 1) {
                ValidatorUtils.checkArgument(this.intentType != null, "intent_type is required when action_type = 1");

                isTrue = this.intentType == 0 ||
                        this.intentType == 1;
                ValidatorUtils.checkArgument(isTrue, "intent_type should be one of 0: Set to open the application custom page through intent," +
                        " 1: set to open the application custom page by action");
            }

            if (this.actionType == 1 || this.actionType == 2) {
                ValidatorUtils.checkArgument(StringUtils.isNotBlank(this.intent), "intent is required wheny action_type = 1 or action_type = 2");
            }


        }
    }

    public static class Builder {
        private String name;
        private Integer actionType;
        private Integer intentType;
        private String intent;
        private String data;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setActionType(Integer actionType) {
            this.actionType = actionType;
            return this;
        }

        public Builder setIntentType(Integer intentType) {
            this.intentType = intentType;
            return this;
        }

        public Builder setIntent(String intent) {
            this.intent = intent;
            return this;
        }

        public Builder setData(String data) {
            this.data = data;
            return this;
        }

        public Button build() {
            return new Button(this);
        }
    }
}
