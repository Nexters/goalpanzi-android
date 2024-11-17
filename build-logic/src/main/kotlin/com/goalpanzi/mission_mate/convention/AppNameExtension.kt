package com.goalpanzi.mission_mate.convention

import org.gradle.api.Project

fun Project.setNamespace(name: String) {
    androidExtension.apply {
        namespace = "com.goalpanzi.mission_mate.$name"
    }
}