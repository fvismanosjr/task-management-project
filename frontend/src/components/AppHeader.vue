<script setup lang="ts">
import { Button } from '@/components/ui/button'
import { logout } from '@/services/auth';
import { useRouter } from 'vue-router';
import { useUserStore } from "@/stores/user";

const user = useUserStore();
const router = useRouter();
const logoutUser = async () => {
    await logout();
    user.destroy();
    router.push({ name: "login" });
}
</script>

<template>
    <header class="bg-white">
        <div class="flex h-16 items-center justify-between px-4 text-sm">
            <slot name="board-name">
                <div></div>
            </slot>
            <div class="flex items-center gap-3">
                <div>
                    <slot name="board-member"></slot>
                </div>
                <Button variant="secondary" @click.prevent="logoutUser">Logout</Button>
            </div>
        </div>
    </header>
</template>
