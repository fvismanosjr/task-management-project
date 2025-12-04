<script setup lang="ts">
import {
    Avatar,
    AvatarFallback,
} from '@/components/ui/avatar'

import BoardDialog from '@/components/BoardDialog.vue'
import BoardList from '@/components/BoardList.vue';
import AppHeader from '@/components/AppHeader.vue';
import { initials } from '@/helpers/StringHelper';
import { useUserStore } from "@/stores/user"
import { ref } from 'vue'

const user = useUserStore();
const boardListKey = ref(0);
const boardDialogKey = ref(0);
const isBoardDialogOpen = ref(false);
const boardDialogId = ref(0);

const updateDialogStatus = (status: boolean) => {
    isBoardDialogOpen.value = status;
    boardDialogId.value = 0;
    boardDialogKey.value++;
}

const openDialog = (id: number) => {
    isBoardDialogOpen.value = true;
    boardDialogId.value = id;
    boardDialogKey.value++;
}

const refresh = (val: boolean) => {
    if (val) {
        boardListKey.value++;
    }
}

</script>

<template>
    <AppHeader>
        <template #board-name>Boards</template>
        <template #board-member>
            <Avatar>
                <AvatarFallback>{{ initials(user.user.username) }}</AvatarFallback>
            </Avatar>
        </template>
    </AppHeader>
    <div class="flex flex-col items-center justify-center gap-6 p-6 md:p-10">
        <div class="flex w-full max-w-lg flex-col gap-6">
            <BoardDialog
                v-if="user.isOwner"
                :id="boardDialogId"
                :is-open="isBoardDialogOpen"
                :key="`board-dialog-${boardDialogKey}`"
                @reload-component="refresh"
                @update:open="updateDialogStatus"
            />

            <BoardList
                :key="`board-list-${boardListKey}`"
                @update:open-dialog="openDialog"
            />
        </div>
    </div>
</template>
