#include<iostream>
#include<ctime>
#include<omp.h>
#include<queue>

using namespace std;

class Graph{
	
	int **adj;
	int n;
	
	
	public:
		
		Graph(int v){
		    n=v;
		    
		    adj=new int*[n];
		    
		    for(int i=0;i<n;i++){
		    	adj[i]=new int[n];
			}
					
		}
		
		
		void addEdge(int v,int w){
			adj[v][w]=1;
		}
		
		
		void SEQbfs(int src){
			
			queue<int> q;
			q.push(src);
			int vis[n]={0};
			
			vis[src]=1;
			
			while(!q.empty())
			{
				int node=q.front();
				q.pop();
				
				for(int i=0;i<n;i++){
					if(adj[node][i] and !vis[i]){
						vis[i]=1;
						q.push(i);
					}
				}
			}
		}
		
		void PARbfs(int src){
				queue<int> q;
			q.push(src);
			int vis[n]={0};
			
			vis[src]=1;
			
			while(!q.empty())
			{
				#pragma omp parallel
				{
					int node;
					#pragma omp single nowait
					{
						node=q.front();
				        q.pop();
				 	}
				 	
				 	#pragma omp for
				 	for(int i=0;i<n;i++){
					 if(adj[node][i] and !vis[i]){
						vis[i]=1;
						#pragma omp critical
						{
							q.push(i);
						}
						
					 }
				  }
				}
		
			}
		}
	
	
};


int main(){
	
	int n=50000;
	Graph g(50000);
	
	for(int i=0;i<n;i++)
	{
		for(int j=0;j<n;j++){
			if(i!=j) g.addEdge(i,j);
		}
	}
	
	
	clock_t seqst=clock();
	g.SEQbfs(0);
	clock_t seqen=clock();
	double duration=(double)(seqen-seqst)/CLOCKS_PER_SEC;
	
	cout<<duration*1000<<endl;
    clock_t parst=clock();
	g.PARbfs(0);
	clock_t paren=clock();
	duration=(double)(paren-parst)/CLOCKS_PER_SEC;
	
	cout<<duration*1000<<endl;
	
}
