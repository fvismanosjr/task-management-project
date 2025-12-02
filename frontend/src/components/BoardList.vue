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
import { ChevronRightIcon, LayoutList, Pencil } from 'lucide-vue-next'
import ConfirmDialog from '@/components/ConfirmDialog.vue'
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { getBoards } from '@/services/board';

const emit = defineEmits<{
    (e: "update:open-dialog", value: number): void
}>();

interface Board {
    id: number,
    name: string,
}

const confirmDialogKey = ref(0);
const router = useRouter();
const boards = ref<Board[]>([]);

getBoards().then((response) => {
    boards.value = response;
})

const openBoardDialog = (id: number) => {
    emit("update:open-dialog", id);
}

const goToBoard = (id: number) => {
    router.push({
        name: "board-profile",
        params: {
            id: id
        }
    })
}

const refresh = async (val: boolean) => {
    if (val) {
        boards.value = [];

        await getBoards().then((response) => {
            boards.value = response;
        });

        confirmDialogKey.value++;
    }
}
</script>

<template>
    <div class="flex w-full flex-col gap-3">
        <template v-for="board in boards" :key="`board-${board.id}`">
            <Item variant="outline">
                <ItemContent>
                    <ItemTitle>{{ board.name }}</ItemTitle>
                </ItemContent>
                <ItemActions>
                    <Button variant="outline" size="icon-sm" @click.prevent="openBoardDialog(board.id)">
                        <Pencil />
                    </Button>
                    <ConfirmDialog
                        :id="board.id"
                        :type="'board'"
                        :key="`confirm-dialog-${board.id}-${confirmDialogKey}`"
                        @reload-component="refresh"
                    />
                    <Button variant="outline" size="icon-sm" @click.prevent="goToBoard(board.id)">
                        <ChevronRightIcon />
                    </Button>
                </ItemActions>
            </Item>
        </template>
        <Empty v-if="!boards.length">
            <EmptyHeader>
                <EmptyMedia variant="icon">
                    <LayoutList />
                </EmptyMedia>
            </EmptyHeader>
            <EmptyTitle>No Board Found</EmptyTitle>
            <EmptyDescription>Create new board and I will show you those</EmptyDescription>
        </Empty>
    </div>
</template>
