<script setup lang="ts">
import {
    Avatar,
    AvatarFallback,
} from '@/components/ui/avatar'

import AppHeader from '@/components/AppHeader.vue';
import TaskList from '@/components/TaskList.vue';
import TaskDialog from '@/components/TaskDialog.vue'
import { initials } from '@/helpers/StringHelper';
import { findBoard } from '@/services/board';
import { useRoute } from 'vue-router';
import { ref } from 'vue';

const taskListKey = ref(0);
const taskDialogKey = ref(0);
const isTaskDialogOpen = ref(false);
const taskDialogId = ref(0);

const route = useRoute();
const board = ref({
    name: "",
    members: [{
        id: 0,
        username: ""
    }]
});

findBoard(Number(route.params.id)).then((response) => {
    board.value = response;
})

const updateTaskStatus = (status: boolean) => {
    isTaskDialogOpen.value = status;
    taskDialogId.value = 0;
    taskDialogKey.value++;
}

const openDialog = (id: number) => {
    isTaskDialogOpen.value = true;
    taskDialogId.value = id;
    taskDialogKey.value++;
}

const refresh = (val: boolean) => {
    if (val) {
        taskListKey.value++;
    }
}
</script>

<template>
    <AppHeader>
        <template #board-name>
            <div>{{ board.name }}</div>
        </template>
        <template #board-member>
            <div class="*:data-[slot=avatar]:ring-background flex -space-x-2 *:data-[slot=avatar]:ring-2 *:data-[slot=avatar]:grayscale">
                <template v-for="member in board.members.slice(0, 3)" :key="`member-${member.username}`">
                    <Avatar>
                        <AvatarFallback>{{ initials(member.username) }}</AvatarFallback>
                    </Avatar>
                </template>
            </div>
        </template>
    </AppHeader>

    <div class="flex flex-col items-center justify-center gap-6 p-6 md:p-10">
        <div class="flex w-full max-w-lg flex-col gap-6">
            <TaskDialog
                :id="taskDialogId"
                :is-open="isTaskDialogOpen"
                :key="`task-dialog-${taskDialogKey}`"
                @reload-component="refresh"
                @update:open="updateTaskStatus"
            />
            <TaskList
                :key="`task-list-${taskListKey}`"
                @update:open-dialog="openDialog"
            />
        </div>
    </div>
</template>
