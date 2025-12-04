<script setup lang="ts">
import {
    ListboxContent,
    ListboxFilter,
    ListboxItem,
    ListboxItemIndicator,
    ListboxRoot,
    useFilter,
    type AcceptableValue
} from 'reka-ui'

import {
    Popover,
    PopoverAnchor,
    PopoverContent,
    PopoverTrigger
} from '@/components/ui/popover'

import {
    TagsInput,
    TagsInputInput,
    TagsInputItem,
    TagsInputItemDelete,
    TagsInputItemText
} from '@/components/ui/tags-input'

import {
    CheckIcon,
    ChevronDown
} from 'lucide-vue-next'

import { Button } from '@/components/ui/button'
import { getUserMembers } from '@/services/boardMember'
import { ref, computed, watch } from 'vue'

const emit = defineEmits<{
    (e: "update:model-value", value: AcceptableValue): void,
}>();

const props = defineProps<{
    members?: string[]
}>()

interface UserMember {
    id: number,
    username: string,
}

const userMembers = ref<UserMember[]>([]);
const selectedMembers = ref<string[]>([]);
const searchTerm = ref('')
const open = ref(false)

getUserMembers().then((response) => {
    userMembers.value = response;
})

if (props.members?.length) {
    selectedMembers.value = props.members;
}

const { contains } = useFilter({ sensitivity: 'base' })

const filteredUserMembers = computed<UserMember[]>(() => {
    const term = searchTerm.value.trim()
    const list = userMembers.value

    return term === ''
            ? list
            : list.filter(u => contains(u.username, term))
})

watch(searchTerm, (f) => {
    if (f) {
        open.value = true
    }
})

const emitUpdateModelValue = (val: AcceptableValue) => {
    emit("update:model-value", val);
}
</script>

<template>
    <Popover v-model:open="open">
        <ListboxRoot
            v-model="selectedMembers"
            highlight-on-hover
            multiple
            @update:model-value="emitUpdateModelValue"
        >
            <PopoverAnchor class="inline-flex w-full">
                <TagsInput @update:model-value="emitUpdateModelValue" v-model="selectedMembers" class="w-full">
                    <TagsInputItem v-for="item in selectedMembers" :key="item.toString()" :value="item.toString()">
                        <TagsInputItemText />
                        <TagsInputItemDelete />
                    </TagsInputItem>

                    <ListboxFilter v-model="searchTerm" as-child>
                        <TagsInputInput placeholder="Members..." @keydown.enter.prevent
                            @keydown.down="open = true" />
                    </ListboxFilter>

                    <PopoverTrigger as-child>
                        <Button size="icon-sm" variant="ghost" class="order-last self-start ml-auto">
                            <ChevronDown class="size-3.5" />
                        </Button>
                    </PopoverTrigger>
                </TagsInput>
            </PopoverAnchor>

            <PopoverContent class="p-1" @open-auto-focus.prevent>
                <ListboxContent
                    class="max-h-[300px] scroll-py-1 overflow-x-hidden overflow-y-auto empty:after:content-['No_options'] empty:p-1 empty:after:block empty:text-sm"
                    tabindex="0">
                    <ListboxItem v-for="item in filteredUserMembers" :key="item.id"
                        class="data-highlighted:bg-accent data-highlighted:text-accent-foreground [&_svg:not([class*=\'text-\'])]:text-muted-foreground relative flex cursor-default items-center gap-2 rounded-sm px-2 py-1.5 text-sm outline-hidden select-none data-disabled:pointer-events-none data-disabled:opacity-50 [&_svg]:pointer-events-none [&_svg]:shrink-0 [&_svg:not([class*=\'size-\'])]:size-4"
                        :value="item.username" @select="() => {
                            searchTerm = ''
                        }">
                        <span>{{ item.username }}</span>

                        <ListboxItemIndicator class="ml-auto inline-flex items-center justify-center">
                            <CheckIcon />
                        </ListboxItemIndicator>
                    </ListboxItem>
                </ListboxContent>
            </PopoverContent>
        </ListboxRoot>
    </Popover>
</template>
