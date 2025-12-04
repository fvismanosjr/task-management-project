// stores/user.js
import { defineStore } from 'pinia'
import { computed, ref } from 'vue'
import type { UserType } from '@/lib/types';

export const useUserStore = defineStore('user', () => {
    const user = ref<UserType>({
        id: 0,
        username: "",
        role: "",
    });

    const isOwner = computed(() => user.value.role == "ROLE_OWNER");
    const isUser = computed(() => user.value.role == "ROLE_USER");

    const save = (payload: Partial<UserType>) => {
        Object.assign(user.value, payload);
    }

    const destroy = () => {
        user.value.id = 0;
        user.value.username = "";
        user.value.role = "";
    }

    return {
        user,
        isOwner,
        isUser,
        save,
        destroy
    }
});
