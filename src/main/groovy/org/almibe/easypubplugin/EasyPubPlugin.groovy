package org.almibe.easypubplugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class EasyPubPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.apply plugin: 'maven'
        project.apply plugin: 'maven-publish'
        project.apply plugin: id "com.jfrog.bintray" version "1.7.3"

        project.task('sourcesJar', type: Jar, dependsOn: classes) {
            classifier = 'sources'
            from sourceSets.main.allSource
        }

        project.task('javadocJar', type: Jar, dependsOn: javadoc) {
            classifier = 'javadoc'
            from javadoc.destinationDir
        }

        project.publishing {
            publications {
                MyPublication(MavenPublication) {
                    from components.java
                    artifact sourcesJar
                    artifact javadocJar
                    groupId 'org.libraryweasel'
                    artifactId 'library-weasel-gradle-plugin'
                    version '0.1.0'
                }
            }
        }

        project.bintray {
            user = project.property('bintray.user')
            key = project.property('bintray.key')
            publications = ['MyPublication']
            pkg {
                repo = 'maven'
                name = 'library-weasel-gradle-plugin'
                userOrg = project.property('bintray.user')
                licenses = ['Mozilla Public License 2.0']
                vcsUrl = 'https://github.com/almibe/library-weasel-gradle-plugin.git'
            }
            pkg {
                version {
                    name = version
                }
            }
        }
    }
}
