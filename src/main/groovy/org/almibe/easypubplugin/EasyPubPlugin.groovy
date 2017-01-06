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
            user = project.property('bintray.user')
            key = project.property('bintray.key')
            publications = ['MyPublication']
            pkg {
                repo = 'maven'
                name = project.name
                userOrg = project.property('bintray.user')
                licenses = [project.property('licenses')]
                vcsUrl = project.property('vcsUrl')
            }
            pkg {
                version {
                    name = project.version
                }
            }
        }
    }
}
