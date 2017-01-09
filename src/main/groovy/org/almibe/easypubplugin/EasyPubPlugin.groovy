package org.almibe.easypubplugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar

class EasyPubPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.apply plugin: 'java'
        project.apply plugin: 'maven'
        project.apply plugin: 'maven-publish'
        project.apply plugin: 'com.jfrog.bintray'

        project.task('sourcesJar', type: Jar, dependsOn: 'classes') {
            classifier = 'sources'
            from project.sourceSets.main.allSource
        }

        project.task('javadocJar', type: Jar, dependsOn: 'javadoc') {
            classifier = 'javadoc'
            from project.javadoc.destinationDir
        }

        project.publishing {
            publications {
                MyPublication(MavenPublication) {
                    from project.components.java
                    artifact project.sourcesJar
                    artifact project.javadocJar
                    groupId project.group
                    artifactId project.name
                    version project.version
                }
            }
        }

        project.bintray {
            user = project.hasProperty('bintray.user') ? project.property('bintray.user') : System.getenv('bintray.user')
            key = project.hasProperty('bintray.key') ? project.hasProperty('bintray.key') : System.getenv('bintray.key')
            publications = ['MyPublication']
            pkg {
                repo = 'maven'
                name = project.name
                licenses = project.property('licenses').tokenize(',')
                vcsUrl = project.property('vcsUrl')
                version {
                    name = project.version
                    released  = new Date()
                    vcsTag = project.version
                }
            }
        }
    }
}
