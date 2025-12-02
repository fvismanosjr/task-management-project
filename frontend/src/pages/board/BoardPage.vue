<script setup lang="ts">

import BoardDialog from '@/components/BoardDialog.vue'
import { ref } from 'vue'
import BoardList from '@/components/BoardList.vue';
import AppHeader from '@/components/AppHeader.vue';

const boardDialogKey = ref(0);
const isBoardDialogOpen = ref(false);
const boardDialogId = ref(0);

const updateDialogStatus = (status: boolean) => {
    isBoardDialogOpen.value = status;
}

const openDialog = (id: number) => {
    isBoardDialogOpen.value = true;
    boardDialogId.value = id;
    boardDialogKey.value++;
}

</script>

<template>
    <AppHeader />
    <div class="flex flex-col items-center justify-center gap-6 p-6 md:p-10">
        <div class="flex w-full max-w-lg flex-col gap-6">
            <BoardDialog
                :id="boardDialogId"
                :is-open="isBoardDialogOpen"
                :key="boardDialogKey"
                @update:open="updateDialogStatus"
            />

            <BoardList @update:open-dialog="openDialog"/>
        </div>
    </div>
</template>
