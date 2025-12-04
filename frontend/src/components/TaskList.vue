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
    ItemDescription,
} from '@/components/ui/item'

import { Button } from '@/components/ui/button'
import { LayoutList, Pencil } from 'lucide-vue-next'
import ConfirmDialog from '@/components/ConfirmDialog.vue'
import type { TaskResponseType } from '@/lib/types';
import { ref } from 'vue';

defineProps<{
    tasks: TaskResponseType[],
}>()

const emit = defineEmits<{
    (e: "update:open-dialog", value: number): void
}>();

const confirmDialogKey = ref(0);
const openTaskDialog = (id: number) => {
    emit("update:open-dialog", id);
}
</script>

<template>
    <div class="flex w-full flex-col gap-3">
        <template v-if="tasks.length">
            <template v-for="task in tasks" :key="`task-${task.id}`">
                <Item variant="outline">
                    <ItemContent>
                        <ItemTitle>{{ task.title }}</ItemTitle>
                        <ItemDescription>{{ task.comment }}</ItemDescription>
                    </ItemContent>
                    <ItemActions>
                        <Button variant="outline" size="icon-sm" @click.prevent="openTaskDialog(task.id)">
                            <Pencil />
                        </Button>
                        <ConfirmDialog
                            :id="task.id"
                            :type="'task'"
                            :key="`confirm-dialog-${task.id}-${confirmDialogKey}`"
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
