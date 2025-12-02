<script setup lang="ts">
import type { SidebarProps } from "@/components/ui/sidebar"

import {
    LayoutDashboard,
    HandCoins,
} from "lucide-vue-next"

import {
    Sidebar,
    SidebarContent,
    SidebarFooter,
    // SidebarGroup,
    // SidebarGroupContent,
    // SidebarGroupLabel,
    SidebarHeader,
    // SidebarInset,
    SidebarMenu,
    SidebarMenuButton,
    SidebarMenuItem,
    // SidebarProvider,
    SidebarRail,
    // SidebarTrigger,
} from '@/components/ui/sidebar'

const props = withDefaults(defineProps<SidebarProps>(), {
    collapsible: "icon",
})

const items = [
    {
        title: 'Dashboard',
        url: '/dashboard',
        icon: LayoutDashboard,
    },
]
</script>

<template>
    <Sidebar v-bind="props">
        <SidebarHeader>
            <SidebarMenu>
                <SidebarMenuItem>
                    <SidebarMenuButton size="lg">
                        <div
                            class="flex aspect-square size-8 items-center justify-center rounded-lg bg-sidebar-primary text-sidebar-primary-foreground">
                            <HandCoins class="size-4" />
                        </div>
                        <div class="grid flex-1 text-left text-sm leading-tight">
                            <span class="truncate font-semibold">Haze</span>
                            <span class="truncate text-xs">Crypto Manager</span>
                        </div>
                    </SidebarMenuButton>
                </SidebarMenuItem>
            </SidebarMenu>
        </SidebarHeader>
        <SidebarContent class="gap-2 p-2">
            <SidebarMenu>
                <SidebarMenuItem v-for="item in items" :key="item.title">
                    <SidebarMenuButton as-child>
                        <a :href="item.url">
                            <component :is="item.icon" />
                            <span>{{ item.title }}</span>
                        </a>
                    </SidebarMenuButton>
                </SidebarMenuItem>
            </SidebarMenu>
        </SidebarContent>
        <SidebarFooter>
            <!-- <NavUser :user="data.user" /> -->
        </SidebarFooter>
        <SidebarRail />
    </Sidebar>
</template>
