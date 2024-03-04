package edu.java.scrapper.client;

import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import edu.java.client.GithubClient;
import edu.java.client.dto.GithubRepositoryResponseDto;
import edu.java.client.implementation.GithubClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.OffsetDateTime;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.*;

@WireMockTest(httpPort = 8081)
public class GithubClientTest {

    private GithubClient githubClient;

    @BeforeEach
    public void setUp() {
        githubClient = new GithubClientImpl("http://localhost:8081");
    }

    @Test
    public void testFetchRepository() {
        final String owner = "torvalds";
        final String repository = "linux";

        stubFor(get(String.format("/repos/%s/%s", owner, repository))
            .willReturn(ok()
                .withHeader("Content-Type", "application/json")
                .withBody("""
                    {
                      "id": 2325298,
                      "node_id": "MDEwOlJlcG9zaXRvcnkyMzI1Mjk4",
                      "name": "linux",
                      "full_name": "torvalds/linux",
                      "private": false,
                      "owner": {
                        "login": "torvalds",
                        "id": 1024025,
                        "node_id": "MDQ6VXNlcjEwMjQwMjU=",
                        "avatar_url": "https://avatars.githubusercontent.com/u/1024025?v=4",
                        "gravatar_id": "",
                        "url": "https://api.github.com/users/torvalds",
                        "html_url": "https://github.com/torvalds",
                        "followers_url": "https://api.github.com/users/torvalds/followers",
                        "following_url": "https://api.github.com/users/torvalds/following{/other_user}",
                        "gists_url": "https://api.github.com/users/torvalds/gists{/gist_id}",
                        "starred_url": "https://api.github.com/users/torvalds/starred{/owner}{/repo}",
                        "subscriptions_url": "https://api.github.com/users/torvalds/subscriptions",
                        "organizations_url": "https://api.github.com/users/torvalds/orgs",
                        "repos_url": "https://api.github.com/users/torvalds/repos",
                        "events_url": "https://api.github.com/users/torvalds/events{/privacy}",
                        "received_events_url": "https://api.github.com/users/torvalds/received_events",
                        "type": "User",
                        "site_admin": false
                      },
                      "html_url": "https://github.com/torvalds/linux",
                      "description": "Linux kernel source tree",
                      "fork": false,
                      "url": "https://api.github.com/repos/torvalds/linux",
                      "forks_url": "https://api.github.com/repos/torvalds/linux/forks",
                      "keys_url": "https://api.github.com/repos/torvalds/linux/keys{/key_id}",
                      "collaborators_url": "https://api.github.com/repos/torvalds/linux/collaborators{/collaborator}",
                      "teams_url": "https://api.github.com/repos/torvalds/linux/teams",
                      "hooks_url": "https://api.github.com/repos/torvalds/linux/hooks",
                      "issue_events_url": "https://api.github.com/repos/torvalds/linux/issues/events{/number}",
                      "events_url": "https://api.github.com/repos/torvalds/linux/events",
                      "assignees_url": "https://api.github.com/repos/torvalds/linux/assignees{/user}",
                      "branches_url": "https://api.github.com/repos/torvalds/linux/branches{/branch}",
                      "tags_url": "https://api.github.com/repos/torvalds/linux/tags",
                      "blobs_url": "https://api.github.com/repos/torvalds/linux/git/blobs{/sha}",
                      "git_tags_url": "https://api.github.com/repos/torvalds/linux/git/tags{/sha}",
                      "git_refs_url": "https://api.github.com/repos/torvalds/linux/git/refs{/sha}",
                      "trees_url": "https://api.github.com/repos/torvalds/linux/git/trees{/sha}",
                      "statuses_url": "https://api.github.com/repos/torvalds/linux/statuses/{sha}",
                      "languages_url": "https://api.github.com/repos/torvalds/linux/languages",
                      "stargazers_url": "https://api.github.com/repos/torvalds/linux/stargazers",
                      "contributors_url": "https://api.github.com/repos/torvalds/linux/contributors",
                      "subscribers_url": "https://api.github.com/repos/torvalds/linux/subscribers",
                      "subscription_url": "https://api.github.com/repos/torvalds/linux/subscription",
                      "commits_url": "https://api.github.com/repos/torvalds/linux/commits{/sha}",
                      "git_commits_url": "https://api.github.com/repos/torvalds/linux/git/commits{/sha}",
                      "comments_url": "https://api.github.com/repos/torvalds/linux/comments{/number}",
                      "issue_comment_url": "https://api.github.com/repos/torvalds/linux/issues/comments{/number}",
                      "contents_url": "https://api.github.com/repos/torvalds/linux/contents/{+path}",
                      "compare_url": "https://api.github.com/repos/torvalds/linux/compare/{base}...{head}",
                      "merges_url": "https://api.github.com/repos/torvalds/linux/merges",
                      "archive_url": "https://api.github.com/repos/torvalds/linux/{archive_format}{/ref}",
                      "downloads_url": "https://api.github.com/repos/torvalds/linux/downloads",
                      "issues_url": "https://api.github.com/repos/torvalds/linux/issues{/number}",
                      "pulls_url": "https://api.github.com/repos/torvalds/linux/pulls{/number}",
                      "milestones_url": "https://api.github.com/repos/torvalds/linux/milestones{/number}",
                      "notifications_url": "https://api.github.com/repos/torvalds/linux/notifications{?since,all,participating}",
                      "labels_url": "https://api.github.com/repos/torvalds/linux/labels{/name}",
                      "releases_url": "https://api.github.com/repos/torvalds/linux/releases{/id}",
                      "deployments_url": "https://api.github.com/repos/torvalds/linux/deployments",
                      "created_at": "2011-09-04T22:48:12Z",
                      "updated_at": "2024-02-23T15:30:57Z",
                      "pushed_at": "2024-02-23T09:58:23Z",
                      "git_url": "git://github.com/torvalds/linux.git",
                      "ssh_url": "git@github.com:torvalds/linux.git",
                      "clone_url": "https://github.com/torvalds/linux.git",
                      "svn_url": "https://github.com/torvalds/linux",
                      "homepage": "",
                      "size": 4911329,
                      "stargazers_count": 166340,
                      "watchers_count": 166340,
                      "language": "C",
                      "has_issues": false,
                      "has_projects": true,
                      "has_downloads": true,
                      "has_wiki": false,
                      "has_pages": false,
                      "has_discussions": false,
                      "forks_count": 51043,
                      "mirror_url": null,
                      "archived": false,
                      "disabled": false,
                      "open_issues_count": 310,
                      "license": {
                        "key": "other",
                        "name": "Other",
                        "spdx_id": "NOASSERTION",
                        "url": null,
                        "node_id": "MDc6TGljZW5zZTA="
                      },
                      "allow_forking": true,
                      "is_template": false,
                      "web_commit_signoff_required": false,
                      "topics": [

                      ],
                      "visibility": "public",
                      "forks": 51043,
                      "open_issues": 310,
                      "watchers": 166340,
                      "default_branch": "master",
                      "temp_clone_token": null,
                      "network_count": 51043,
                      "subscribers_count": 8334
                    }""")));
        GithubRepositoryResponseDto response = githubClient.fetchRepository(owner, repository);

        assertThat(response)
            .isNotNull()
            .extracting(
                GithubRepositoryResponseDto::id,
                GithubRepositoryResponseDto::name,
                GithubRepositoryResponseDto::lastActivityDate
            )
            .containsExactly(
                2325298L,
                String.format("%s/%s", owner, repository),
                OffsetDateTime.parse("2024-02-23T15:30:57Z")
            );
    }
}
