<script setup lang="ts">
import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogFooter,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
    DialogClose,
} from '@/components/ui/dialog'

import { Input } from '@/components/ui/input'
import { Label } from '@/components/ui/label'
import { Button } from '@/components/ui/button'
import { Textarea } from '@/components/ui/textarea'
import { Plus } from "lucide-vue-next"
import TaskMemberSelect from '@/components/TaskMemberSelect.vue'
import { ref } from 'vue'

const props = defineProps<{
    id?: number,
    type?: string,
    isOpen: boolean
}>()

const emit = defineEmits<{
    (e: "update:open", value: boolean): void,
    (e: "reload-table", value: boolean): void,
}>();

const task = ref({
    id: 0,
    title: "",
    comment: "",
});

const submitTask = () => {
    // switch (props.type) {
    //     case "delete":
    //         deletePortfolio(portfolio.value.id);
    //         break;

    //     default:
    //         savePortfolio(portfolio.value);
    //         break;
    // }

    emit("update:open", false);
    emit("reload-table", true);
}
</script>

<template>
    <Dialog :open="isOpen" @update:open="(val) => emit('update:open', val)">
        <DialogTrigger>
            <Button size="sm">
                <Plus />Add Task
            </Button>
        </DialogTrigger>
        <DialogContent>
            <DialogHeader>
                <DialogTitle>Task</DialogTitle>
                <DialogDescription>Donec quis arcu condimentum, egestas elit tempus, varius sapien. </DialogDescription>
            </DialogHeader>
            <div class="grid gap-4">
                <div class="grid gap-3">
                    <Label for="title">Title</Label>
                    <Input
                        v-model="task.title"
                        id="title"
                        name="title"
                        placeholder="Title name"
                        :disabled="props.type == 'delete'"
                    />
                </div>
                <div class="grid gap-3">
                    <Label for="assignee">Assignee</Label>
                    <TaskMemberSelect/>
                </div>
                <div class="grid gap-3">
                    <Label for="comment">Comment</Label>
                    <Textarea v-model="task.comment" id="comment" placeholder="Type your comment here." />
                </div>
            </div>
            <DialogFooter>
                <DialogClose as-child>
                    <Button variant="outline">
                        Cancel
                    </Button>
                </DialogClose>
                <Button type="button" @click.prevent="submitTask">
                    Save
                </Button>
            </DialogFooter>
        </DialogContent>
    </Dialog>
</template>
