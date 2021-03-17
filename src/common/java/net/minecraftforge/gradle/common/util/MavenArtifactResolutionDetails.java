/*
 * ForgeGradle
 * Copyright (C) 2018 Forge Development LLC
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 */

package net.minecraftforge.gradle.common.util;

import org.gradle.api.artifacts.ModuleIdentifier;
import org.gradle.api.artifacts.component.ModuleComponentIdentifier;
import org.gradle.api.attributes.AttributeContainer;
import org.gradle.api.internal.artifacts.repositories.ArtifactResolutionDetails;
import org.gradle.api.internal.attributes.ImmutableAttributes;

import javax.annotation.Nullable;

class MavenArtifactResolutionDetails implements ArtifactResolutionDetails
{

    private final ModuleIdentifier moduleIdentifier;
    private final ModuleComponentIdentifier moduleComponentIdentifier;
    private final String consumerName;
    private final ImmutableAttributes consumerAttributes;
    protected boolean wontBeFound;

    MavenArtifactResolutionDetails(Artifact artifact, String consumerName) {
        this.consumerName = consumerName;
        this.moduleIdentifier = new ModuleIdentifier() {
            @Override
            public String getGroup() {
                return artifact.getGroup();
            }

            @Override
            public String getName() {
                return artifact.getName();
            }
        };
        this.moduleComponentIdentifier = new ModuleComponentIdentifier() {
            @Override
            public String getGroup() {
                return getModuleIdentifier().getGroup();
            }

            @Override
            public String getModule() {
                return getModuleIdentifier().getName();
            }

            @Override
            public String getVersion() {
                return artifact.getVersion();
            }

            @Override
            public ModuleIdentifier getModuleIdentifier() {
                return moduleIdentifier;
            }

            @Override
            public String getDisplayName() {
                return artifact.getDescriptor();
            }
        };
        this.consumerAttributes = ImmutableAttributes.EMPTY;
    }

    @Override
    public ModuleIdentifier getModuleId() {
        return moduleIdentifier;
    }

    @Override
    @Nullable
    public ModuleComponentIdentifier getComponentId() {
        return moduleComponentIdentifier;
    }

    @Override
    public AttributeContainer getConsumerAttributes() {
        return consumerAttributes;
    }

    @Override
    public String getConsumerName() {
        return consumerName;
    }

    @Override
    public boolean isVersionListing() {
        return moduleComponentIdentifier == null;
    }

    @Override
    public void notFound() {
        wontBeFound = true;
    }
}
