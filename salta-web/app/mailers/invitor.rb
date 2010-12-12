class Invitor < ActionMailer::Base
  def invite(opts)
    @link = opts[:link]
    @group_name = opts[:group_name]
    mail(:from => opts[:from],
         :to => opts[:to],
         :subject => "You are invited to join Salta Group #{opts[:group_name]}"
    )
    sent_on Time.now
  end
end