package com.zclcs.cloud.lib.core.strategy;

import jakarta.validation.groups.Default;

public interface ValidGroups extends Default {

    interface Crud extends ValidGroups {
        interface Create extends Crud {

        }

        interface Update extends Crud {

        }

        interface Query extends Crud {

        }

        interface Delete extends Crud {

        }
    }

}
