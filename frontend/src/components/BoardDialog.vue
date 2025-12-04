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
import { Plus } from "lucide-vue-next"
import BoardMemberSelect from '@/components/BoardMemberSelect.vue'
import type { BoardMemberType, BoardWithMembersType } from '@/lib/types'
import { ref } from 'vue'
import { findBoard, saveBoard, updateBoard } from '@/services/board'
import type { AcceptableValue } from 'reka-ui'

const props = defineProps<{
    id?: number,
    isOpen: boolean
}>()

const emit = defineEmits<{
    (e: "update:open", value: boolean): void,
    (e: "reload-component", value: boolean): void,
}>();

const boardMemberSelectKey = ref(0);
const board = ref<BoardWithMembersType>({
    id: 0,
    name: '',
    members: []
});

if (props.id) {
    findBoard(props.id).then((response) => {
        board.value.id = response.id;
        board.value.name = response.name;
        board.value.members = response.members.map((member: BoardMemberType) => member.username);
        boardMemberSelectKey.value++;
    })
}

const selectedMembers = (val: AcceptableValue) => {
    board.value.members = Array.isArray(val) ? val : [];
}

const submitBoard = async () => {

    if (props.id) {
        await updateBoard(props.id, board.value)
    } else {
        await saveBoard(board.value);
    }

    emit("update:open", false);
    emit("reload-component", true);
}
</script>

<template>
    <Dialog :open="isOpen" @update:open="(val) => emit('update:open', val)">
        <DialogTrigger>
            <Button size="sm">
                <Plus />Add Board
            </Button>
        </DialogTrigger>
        <DialogContent>
            <DialogHeader>
                <DialogTitle>Board</DialogTitle>
                <DialogDescription>Donec quis arcu condimentum, egestas elit tempus, varius sapien. </DialogDescription>
            </DialogHeader>
            <div class="grid gap-4">
                <div class="grid gap-3">
                    <Label for="name-1">Name</Label>
                    <Input
                        v-model="board.name"
                        id="name-1"
                        name="name"
                        placeholder="Board name"
                    />
                </div>
                <div class="grid gap-3">
                    <Label for="members">Member</Label>
                    <BoardMemberSelect
                        :key="`board-member-select-${boardMemberSelectKey}`"
                        :members="board.members"
                        @update:model-value="selectedMembers"
                    />
                </div>
            </div>
            <DialogFooter>
                <DialogClose as-child>
                    <Button variant="outline">
                        Cancel
                    </Button>
                </DialogClose>
                <Button type="button" @click.prevent="submitBoard">
                    Save
                </Button>
            </DialogFooter>
        </DialogContent>
    </Dialog>
</template>
