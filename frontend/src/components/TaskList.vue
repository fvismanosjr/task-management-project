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
import TaskDialog from '@/components/TaskDialog.vue'
import { ref } from 'vue';

interface Task {
    id: number,
    title: string,
}

const isTaskDialogOpen = ref(false);

const tasks = ref<Task[]>([
    {
        id: 1,
        title: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam placerat sapien ac aliquam varius."
    },
    {
        id: 2,
        title: "Other Board"
    }
]);
</script>

<template>
    <div class="flex w-full max-w-lg mx-auto flex-col gap-3">
        <TaskDialog :is-open="isTaskDialogOpen" @update:open="(val) => isTaskDialogOpen = val"/>
        <template v-if="tasks.length">
            <template v-for="task in tasks" :key="`task-${task.id}`">
                <Item variant="outline">
                    <ItemContent>
                        <ItemTitle>{{ task.title }}</ItemTitle>
                    </ItemContent>
                    <ItemActions>
                        <Button variant="outline" size="icon-sm">
                            <Pencil />
                        </Button>
                        <ConfirmDialog />
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
