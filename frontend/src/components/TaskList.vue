<script setup lang="ts">
import {
    Empty,
    EmptyDescription,
    EmptyHeader,
    EmptyMedia,
    EmptyTitle,
} from '@/components/ui/empty'

import {
    Item,
    ItemActions,
    ItemContent,
    ItemTitle,
} from '@/components/ui/item'

import { Button } from '@/components/ui/button'
import { LayoutList, Pencil } from 'lucide-vue-next'
import ConfirmDialog from '@/components/ConfirmDialog.vue'
import { onUnmounted, ref } from 'vue';
import { useRoute } from 'vue-router'
import { getTasks } from '@/services/task'
import SocketService from '@/services/socket'
import type { TaskType } from '@/lib/types';

const emit = defineEmits<{
    (e: "update:open-dialog", value: number): void
}>();

const route = useRoute();
const confirmDialogKey = ref(0);
const tasks = ref<TaskType[]>([]);

getTasks(Number(route.params.id)).then((response) => {
    tasks.value = response;

    SocketService.connect(() => {
        SocketService.subscribeToTasks(Number(route.params.id), (newTasks: TaskType[]) => {
            tasks.value = newTasks;
        });
    });
})

const openTaskDialog = (id: number) => {
    emit("update:open-dialog", id);
}

const refresh = async (val: boolean) => {
    if (val) {
        tasks.value = [];

        await getTasks(Number(route.params.id)).then((response) => {
            tasks.value = response;
        });

        confirmDialogKey.value++;
    }
}

onUnmounted(() => {
    SocketService.disconnect();
})
</script>

<template>
    <div class="flex w-full flex-col gap-3">
        <template v-if="tasks.length">
            <template v-for="task in tasks" :key="`task-${task.id}`">
                <Item variant="outline">
                    <ItemContent>
                        <ItemTitle>{{ task.title }}</ItemTitle>
                    </ItemContent>
                    <ItemActions>
                        <Button variant="outline" size="icon-sm" @click.prevent="openTaskDialog(task.id)">
                            <Pencil />
                        </Button>
                        <ConfirmDialog
                            :id="task.id"
                            :type="'task'"
                            :key="`confirm-dialog-${task.id}-${confirmDialogKey}`"
                            @reload-component="refresh"
                        />
                    </ItemActions>
                </Item>
            </template>
        </template>
        <Empty v-else>
            <EmptyHeader>
                <EmptyMedia variant="icon">
                    <LayoutList />
                </EmptyMedia>
            </EmptyHeader>
            <EmptyTitle>Board is Empty</EmptyTitle>
            <EmptyDescription>Create new task and I will show you those</EmptyDescription>
        </Empty>
    </div>
</template>
